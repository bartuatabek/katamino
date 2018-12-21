import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
       String selectedPlayerName = ((KataminoButton) event.getSource()).getButtonName();
        Player player;
        if (savedPlayers.contains(selectedPlayerName)){
            player = fm.loadPlayer(selectedPlayerName);
        }
        else{
            player = new Player(selectedPlayerName);
        }

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
    public void createButtonClicked(){
//        String newPlayerName = playerNameField.getText();
//        errorLabel.setText("");
//        if(!newPlayerName.isEmpty() && !newPlayerName.endsWith(" ")){
//            if (!players.contains(newPlayerName)) {
//            System.out.println(newPlayerName);
//            players.add(newPlayerName);
//            valueFactory.setValue(newPlayerName);
//            spinner.setValueFactory(valueFactory);
//            }
//            else{
//                errorLabel.setText("Player Name Already Taken!!");
//            }
//        }
//        else{
//        // TODO: can add more
//        errorLabel.setText("Invalid Player Name!!");
//        }
    }
    @FXML
    public void backButtonClicked(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("modeSelectionMenu.fxml"));
        root.getChildren().setAll(pane);
    }

}
