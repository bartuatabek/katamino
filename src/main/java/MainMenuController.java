import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import kataminoButton.KataminoButton;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    @FXML
    private KataminoButton singleplayerButton;

    @FXML
    private KataminoButton multiplayerButton;

    @FXML
    private KataminoButton highScoreMenuButton;

    @FXML
    private KataminoButton settings;

    @FXML
    private MediaView background;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        singleplayerButton.setButtonName("Singleplayer");
        multiplayerButton.setButtonName("Multiplayer");
        highScoreMenuButton.setButtonName("Highscore");
        settings.setButtonName("Settings");

        // initialize the media player
        MediaPlayer player = new MediaPlayer(new Media(getClass().getResource("background.mp4").toExternalForm()));
        player.setAutoPlay(true);
        player.setCycleCount(MediaPlayer.INDEFINITE);
        background.setMediaPlayer(player);
    }

    @FXML
    public void singlePlayerButtonClicked(MouseEvent event) throws IOException {
        FXMLLoader playerSelectionLoader = new FXMLLoader(getClass().getResource("modeSelectionMenu.fxml"));
        Parent levelPane = playerSelectionLoader.load();
        Scene levelMenuScene = new Scene(levelPane, 750, 450);

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(levelMenuScene);

        FXMLLoader.load(getClass().getResource("/view/Profile.fxml"));
    }

    @FXML
    public void highScoreMenuButtonClicked(MouseEvent event) throws IOException {
        FXMLLoader highScoreLoader = new FXMLLoader(getClass().getResource("highScore.fxml"));
        Parent highScorePane = highScoreLoader.load();
        Scene highScoreScene = new Scene(highScorePane, 750, 450);

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(highScoreScene);
    }

    @FXML
    public void creditsButtonClicked(MouseEvent event) throws IOException {
        FXMLLoader highScoreLoader = new FXMLLoader(getClass().getResource("credits.fxml"));
        Parent highScorePane = highScoreLoader.load();
        Scene highScoreScene = new Scene(highScorePane, 750, 450);

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(highScoreScene);
    }



}
