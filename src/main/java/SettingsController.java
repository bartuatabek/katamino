import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import kataminoSoundButton.KataminoSoundButton;
import kataminoSoundSlider.KataminoSoundSlider;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    @FXML
    KataminoSoundButton soundButton;

    @FXML
    KataminoSoundSlider soundSlider;

    @FXML
    private AnchorPane root;

    @FXML
    boolean mute;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mute = true;
    }
    @FXML
    public void backButtonClicked(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        root.getChildren().setAll(pane);
    }
    @FXML
    public void soundkButtonClicked(MouseEvent event) throws IOException {
        soundButton.sound(mute);
        mute = !mute;
    }

}
