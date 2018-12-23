package kataminoLongButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class KataminoLongButton extends Pane {
    @FXML
    private Label label;

    @FXML
    private ImageView imageView;

    public KataminoLongButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("kataminoLongButton.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }

    public void setButtonName(String name){
        label.setText(name);
    }
    public void setFontSize(int size){
        label.setStyle("-fx-font-size:" + size);
    }
}
