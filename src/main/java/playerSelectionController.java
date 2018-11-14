import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import kataminoButton.KataminoButton;
import java.net.URL;
import java.util.ResourceBundle;
public class playerSelectionController implements Initializable {

    @FXML private Spinner spinner;
    @FXML private KataminoButton continueButton;
    @FXML private KataminoButton createPlayerButton;
    @FXML private TextField playerNameField;
    @FXML private Label errorLabel;
    private String[] playerList;
    private ObservableList<String> players = FXCollections.observableArrayList();
    private SpinnerValueFactory<String> valueFactory;
    public playerSelectionController() {
    
    }

    public void initialize(URL location, ResourceBundle resources) {
        continueButton.setButtonName("Continue");
        createPlayerButton.setButtonName("Create");
    }
    public void setSpinner(){
        players.addAll(playerList);
        valueFactory = //
                new SpinnerValueFactory.ListSpinnerValueFactory<String>(players);
        valueFactory.setValue(players.get(0));
        spinner.setValueFactory(valueFactory);
    }

    @FXML
    public void continueButtonClicked(){
        String selectedPlayerName = (String) spinner.getValue();
        System.out.print(selectedPlayerName);
        // get the spinner value
        // continue to the next screen
    }

    @FXML
    public void createButtonClicked(){
        String newPlayerName = playerNameField.getText();
        errorLabel.setText("");
        // Todo: needs logic like should not contain " " etc
        if (!players.contains(newPlayerName)) {
            // Todo: Create a player with name newplayername
            System.out.println(newPlayerName);
            players.add(newPlayerName);
            valueFactory.setValue(newPlayerName);
            spinner.setValueFactory(valueFactory); // We can change these if not liked
        }
        else{
            errorLabel.setText("Player Name Already Taken!!");
        }

    }
    public void setPlayerList(String[] playerList) {
        this.playerList = playerList;
    }
}
