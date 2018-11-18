package kataminoSoundButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class KataminoSoundButton extends Pane {
    @FXML
    private ImageView imageView;

    public KataminoSoundButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("kataminoSoundButton.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }
    public void sound(boolean mute){
        if(mute){
            imageView.setImage(new Image("resources/kataminoMutedSoundButton.png"));
        }
        else {
            imageView.setImage(new Image("resources/kataminoSoundButton.png"));
        }
    }
}
