package kataminoHintButton;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class KataminoHintButton extends Pane {
    public KataminoHintButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("kataminoHintButton.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }
}
