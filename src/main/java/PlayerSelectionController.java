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
import kataminoPlayerAddButton.KataminoPlayerAddButton;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
public class PlayerSelectionController implements Initializable {

    @FXML
    private Spinner spinner;

    @FXML
    private KataminoButton continueButton;

    @FXML
    private KataminoPlayerAddButton createPlayerButton;

    @FXML
    private TextField playerNameField;

    @FXML
    private Label errorLabel;

    @FXML
    private KataminoBackButton backButton;

    @FXML
    private AnchorPane root;

    private ObservableList<String> players;
    private SpinnerValueFactory<String> valueFactory;
    private FileManager fm;
    private ArrayList<String> savedPlayers;
    public PlayerSelectionController() {
        fm = new FileManager();
        players = FXCollections.observableArrayList();
        valueFactory = //
                new SpinnerValueFactory.ListSpinnerValueFactory<String>(players);
        savedPlayers = fm.loadPlayerNames();
        players.addAll(savedPlayers);
    }

    public void initialize(URL location, ResourceBundle resources) {
        continueButton.setButtonName("Continue");

        valueFactory.setValue(players.get(0));
        spinner.setValueFactory(valueFactory);
    }

    @FXML
    public void continueButtonClicked(MouseEvent event) throws IOException {
        String selectedPlayerName = (String) spinner.getValue();
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
        String newPlayerName = playerNameField.getText();
        errorLabel.setText("");
        if(!newPlayerName.isEmpty() && !newPlayerName.endsWith(" ")){
            if (!players.contains(newPlayerName)) {
            System.out.println(newPlayerName);
            players.add(newPlayerName);
            valueFactory.setValue(newPlayerName);
            spinner.setValueFactory(valueFactory);
            }
            else{
                errorLabel.setText("Player Name Already Taken!!");
            }
        }
        else{
        // TODO: can add more
        errorLabel.setText("Invalid Player Name!!");
        }
    }
    @FXML
    public void backButtonClicked(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("modeSelectionMenu.fxml"));
        root.getChildren().setAll(pane);
    }

}
