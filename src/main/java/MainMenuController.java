import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
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

    @FXML
    private AnchorPane root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        singleplayerButton.setButtonName("Singleplayer");
        multiplayerButton.setButtonName("Multiplayer");
        highScoreMenuButton.setButtonName("Highscore");
        settings.setButtonName("Settings");

        // initialize the media player
//        Media media = new Media(new File("src/main/resources/background.mp4").toURI().toString());
//        MediaPlayer player = new MediaPlayer(media);
//        player.setAutoPlay(true);
//        player.setCycleCount(MediaPlayer.INDEFINITE);
//        background.setMediaPlayer(player);

    }
    @FXML
    public void multiplayerButtonClicked(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("boardMenu.fxml"));
        root.getChildren().setAll(pane);
    }
    @FXML
    public void singlePlayerButtonClicked(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("modeSelectionMenu.fxml"));
        root.getChildren().setAll(pane);
    }

    @FXML
    public void settingsButtonClicked(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("settings.fxml"));
        root.getChildren().setAll(pane);
    }

    @FXML
    public void highScoreMenuButtonClicked(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("highScore.fxml"));
        root.getChildren().setAll(pane);
    }

    @FXML
    public void creditsButtonClicked(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("credits.fxml"));
        root.getChildren().setAll(pane);
    }
}
