import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import kataminoBackButton.KataminoBackButton;
import kataminoButton.KataminoButton;
import kataminoPlayerAddButton.KataminoPlayerAddButton;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
public class PlayerSelectionController implements Initializable {

    @FXML private Spinner spinner;
    @FXML private KataminoButton continueButton;
    @FXML private KataminoPlayerAddButton createPlayerButton;
    @FXML private TextField playerNameField;
    @FXML private Label errorLabel;
    @FXML private KataminoBackButton backButton;
    private ObservableList<String> players;
    private SpinnerValueFactory<String> valueFactory;
    private FileManager fm;
    private ArrayList<String> savedPlayers;
    public PlayerSelectionController() {

    }

    public void initialize(URL location, ResourceBundle resources) {
        fm = new FileManager();
        continueButton.setButtonName("Continue");
        players = FXCollections.observableArrayList();
        savedPlayers = fm.loadPlayerNames();
        players.addAll(savedPlayers);
        valueFactory = //
                new SpinnerValueFactory.ListSpinnerValueFactory<String>(players);
        valueFactory.setValue(players.get(0));
        spinner.setValueFactory(valueFactory);
    }

    @FXML
    public void continueButtonClicked(){
        String selectedPlayerName = (String) spinner.getValue();
        System.out.print("Selected Player: "+selectedPlayerName);
        if(savedPlayers.contains(selectedPlayerName)){
            Player selectedPlayer = fm.loadPlayer(selectedPlayerName);
        }
        else{
            Player newPlayer = new Player(selectedPlayerName);
        }

        // Todo: setPlayer of something Maybe even savePlayer here with initial values
    }

    @FXML
    public void createButtonClicked(){
        String newPlayerName = playerNameField.getText();
        errorLabel.setText("");
        if(!newPlayerName.endsWith(" ")){
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
        // TODO: can add more
        errorLabel.setText("Player Name Can Not End With Empty Space!!");
    }
}
