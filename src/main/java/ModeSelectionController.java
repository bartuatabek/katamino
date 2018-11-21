import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import kataminoLongButton.KataminoLongButton;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModeSelectionController implements Initializable {
    @FXML KataminoLongButton kataminoArcadeButton;
    @FXML KataminoLongButton kataminoCustomButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        kataminoArcadeButton.setButtonName("Classic(Arcade) Mode");
        kataminoCustomButton.setButtonName("Custom Shapes Mode");
    }
    @FXML
    public void arcadeSelected(MouseEvent event) throws IOException {
        FXMLLoader playerSelectionLoader = new FXMLLoader(getClass().getResource("playerSelection.fxml"));
        Parent levelPane = playerSelectionLoader.load();
        Scene levelMenuScene = new Scene(levelPane, 1200, 700);

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(levelMenuScene);
    }
    @FXML
    public void backButtonClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
        Parent pane = loader.load();
        Scene mainMenuScene = new Scene(pane, 1200, 700);

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(mainMenuScene);

    }
}
