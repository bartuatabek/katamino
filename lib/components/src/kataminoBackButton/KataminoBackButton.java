package kataminoBackButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class KataminoBackButton extends Pane {

    @FXML
    private ImageView imageView;

    public KataminoBackButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("kataminoBackButton.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }
}
