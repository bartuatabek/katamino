package kataminoButton;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class KataminoButton extends Pane {
    @FXML
    private Label label;

    @FXML
    private ImageView imageView;

    public KataminoButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("kataminoButton.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }

    public void setButtonName(String name){
        label.setText(name);
    }

    public String getButtonName() {
        return label.getText();
    }
    public void setFontSize(int size){
        label.setStyle("-fx-font-size:" + size);
    }
}
