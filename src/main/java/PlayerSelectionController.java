import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import javafx.stage.Stage;
import kataminoBackButton.KataminoBackButton;
import kataminoButton.KataminoButton;
import kataminoChangeButton.KataminoChangeButton;
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
        continueButton.setButtonName(players.get(0));
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
                        Player player = new Player(continueButton.getButtonName());
                        fm.saveANewPlayer(player);
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
        Player player = fm.loadPlayer(selectedPlayerName);

        FXMLLoader levelMenuLoader = new FXMLLoader(getClass().getResource("levelMenu.fxml"));
        AnchorPane pane = levelMenuLoader.load();
        LevelMenuController lvlctrl = levelMenuLoader.getController();
        lvlctrl.setPlayer(player);
        lvlctrl.updateLevelAccess();
        root.getChildren().setAll(pane);
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
