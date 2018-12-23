package kataminoLevelButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.io.IOException;

public class KataminoLevelButton extends Pane {
    @FXML
    private Label label;

    @FXML
    private ImageView imageView;

    public KataminoLevelButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("kataminoLevelButton.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }

    public void setButtonText(String text){
        label.setText(text);
    }

    public String getButtonText() {
        return label.getText();
    }
}
