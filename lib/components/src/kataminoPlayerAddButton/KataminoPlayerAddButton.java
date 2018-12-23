package kataminoPlayerAddButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class KataminoPlayerAddButton extends Pane {
    @FXML
    private ImageView imageView;

    public KataminoPlayerAddButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("kataminoPlayerAddButton.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }
}
