import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import kataminoButton.KataminoButton;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    @FXML KataminoButton singleplayerButton;
    @FXML KataminoButton multiplayerButton;
    @FXML KataminoButton highScoreMenuButton;
    @FXML KataminoButton settings;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        singleplayerButton.setButtonName("Singleplayer");
        multiplayerButton.setButtonName("Multiplayer");
        highScoreMenuButton.setButtonName("Highscore");
        settings.setButtonName("Settings");

    }

    @FXML
    public void singlePlayerButtonClicked(MouseEvent event) throws IOException {
        FXMLLoader playerSelectionLoader = new FXMLLoader(getClass().getResource("playerSelection.fxml"));
        Parent levelPane = playerSelectionLoader.load();
        Scene levelMenuScene = new Scene(levelPane, 800, 600);

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(levelMenuScene);
    }

    @FXML
    public void highScoreMenuButtonClicked(MouseEvent event) throws IOException {
        FXMLLoader highScoreLoader = new FXMLLoader(getClass().getResource("highScore.fxml"));
        Parent highScorePane = highScoreLoader.load();
        Scene highScoreScene = new Scene(highScorePane, 800, 600);

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(highScoreScene);
    }

    @FXML
    public void creditsButtonClicked(MouseEvent event) throws IOException {
        FXMLLoader highScoreLoader = new FXMLLoader(getClass().getResource("credits.fxml"));
        Parent highScorePane = highScoreLoader.load();
        Scene highScoreScene = new Scene(highScorePane, 800, 600);

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(highScoreScene);
    }



}
