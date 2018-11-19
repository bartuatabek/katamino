import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import kataminoBackButton.KataminoBackButton;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreditsController implements Initializable {

    @FXML private KataminoBackButton kataminoBackButton;

    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    public void backButtonClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
        Parent pane = loader.load();
        Scene mainMenuScene = new Scene(pane, 800, 600);

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(mainMenuScene);
    }
}
