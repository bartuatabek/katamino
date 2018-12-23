import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import kataminoBackButton.KataminoBackButton;
import kataminoButton.KataminoButton;
import kataminoChangeButton.KataminoChangeButton;
import kataminoLevelButton.KataminoLevelButton;
import kataminoPlayerAddButton.KataminoPlayerAddButton;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
public class PlayerSelectionController implements Initializable {

    @FXML
    private KataminoButton continueButton;

    @FXML
    private KataminoPlayerAddButton createPlayerButton;

    @FXML
    private KataminoChangeButton leftButton;

    @FXML
    private KataminoChangeButton rightButton;

    @FXML
    private Label errorLabel;

    @FXML
    private KataminoBackButton backButton;

    @FXML
    private AnchorPane root;


    private ObservableList<String> players;

    private FileManager fm;

    private ArrayList<String> savedPlayers;

    private int playerIndex = 0;

    public void initialize(URL location, ResourceBundle resources) {
        fm = new FileManager();
        players = FXCollections.observableArrayList();
        savedPlayers = fm.loadPlayerNames();
        players.addAll(savedPlayers);
        if(!players.isEmpty())
         continueButton.setButtonName(players.get(0));
        else
            continueButton.setButtonName("");
    }

    public void leftButtonClicked(MouseEvent e) {
        if (playerIndex > 0) {
            playerIndex--;
            continueButton.setButtonName(players.get(playerIndex));
        }
    }

    public void rightButtonClicked(MouseEvent e) {
        if (playerIndex < players.size() - 1) {
            playerIndex++;
            continueButton.setButtonName(players.get(playerIndex));
        }
    }

    @FXML
    public void continueButtonClicked(MouseEvent event) throws IOException {
        errorLabel.setText("");
        continueAction(event);
    }

    public void createPlayer(KeyEvent e) throws IOException {
        if (e.getCode() == KeyCode.ENTER) {
            if (continueButton.isEditing()) {
                continueButton.setEditing(false);
                errorLabel.setText("");
                if (!continueButton.getNameField().isEmpty() && !continueButton.getNameField().endsWith(" ")) {
                    if (!players.contains(continueButton.getButtonName())) {
                        players.add(continueButton.getButtonName());
                        continueAction(e);
                    } else {
                        errorLabel.setText("Player Name Already Taken!!");
                        continueButton.setEditing(true);
                    }
                } else {
                    errorLabel.setText("Invalid Player Name!!");
                    continueButton.setEditing(true);
                }
            }
        }
    }

    EventHandler<KeyEvent> keyPressed = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            try {
                createPlayer(event);
                event.consume();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    };

    public void continueAction(MouseEvent event) throws IOException {

        String selectedPlayerName = ((KataminoButton) event.getSource()).getButtonName();
        Player player;
        ArrayList<String> allPlayerNames= new ArrayList<>();
        for(Player x:fm.getAllPLayers())
        {allPlayerNames.add(x.getPlayerName());}
       if(allPlayerNames.contains(selectedPlayerName))
         player = fm.loadPlayer(selectedPlayerName);
       else {
           player = new Player(selectedPlayerName);
           FileManager fm =new FileManager();
           player.setLatestBoard((fm.loadLevels(1)),0);
           player.setHighScore(0);
           player.setAccessibleLevel(1);
           player.setLatestTime(0);
           fm.saveANewPlayer(player);
       }
        FXMLLoader singLoader = new FXMLLoader(getClass().getResource("singlePlayerGame.fxml"));
        AnchorPane pane = singLoader.load();
        SinglePlayerGameController gm =  singLoader.getController();
      //  ((SinglePlayerGame)gm.game).setPlayer(player);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        gm.gameSetup(player.getAccessibleLevel(), player.getHighScore(), player);

        stage.setWidth(1250);
        stage.setHeight(700);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        root.getChildren().setAll(pane);
/*
        String selectedPlayerName = ((KataminoButton) event.getSource()).getButtonName();
        Player player = fm.loadPlayer(selectedPlayerName);
        FXMLLoader levelMenuLoader = new FXMLLoader(getClass().getResource("levelMenu.fxml"));
        AnchorPane pane = levelMenuLoader.load();
        LevelMenuController lvlctrl = levelMenuLoader.getController();
        lvlctrl.setPlayer(player);
        lvlctrl.updateLevelAccess();
        root.getChildren().setAll(pane);*/
    }

    public void continueAction(KeyEvent event) throws IOException {
        String selectedPlayerName = ((KataminoButton) event.getSource()).getButtonName();
        Player player = fm.loadPlayer(selectedPlayerName);

        FXMLLoader levelMenuLoader = new FXMLLoader(getClass().getResource("levelMenu.fxml"));
        AnchorPane pane = levelMenuLoader.load();
        LevelMenuController lvlctrl = levelMenuLoader.getController();
        lvlctrl.setPlayer(player);
        lvlctrl.updateLevelAccess();
        root.getChildren().setAll(pane);
    }

    public static Object getController(Node node) {
        Object controller = null;
        do {
            controller = node.getProperties().get("foo");
            node = node.getParent();
        } while (controller == null && node != null);
        return controller;
    }

    @FXML
    public void createButtonClicked() {
        continueButton.setEditing(true);
        root.getScene().setOnKeyPressed(keyPressed);
    }

    @FXML
    public void backButtonClicked(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("modeSelectionMenu.fxml"));
        root.getChildren().setAll(pane);
    }

}
