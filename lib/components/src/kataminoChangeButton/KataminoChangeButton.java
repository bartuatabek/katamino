package kataminoChangeButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class KataminoChangeButton extends Pane {
    @FXML
    private ImageView imageView;

    public KataminoChangeButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("kataminoChangeButton.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }
}
