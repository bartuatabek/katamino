package kataminoButton;

import java.awt.event.ActionListener;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class KataminoButton extends Pane {
    @FXML
    private Label label;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField nameField;

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

    public void setEditing(Boolean bool) {
        if (bool) {
            nameField.setVisible(true);
            label.setVisible(false);
            nameField.setEditable(true);
        }
        else {
            nameField.setVisible(false);
            label.setVisible(true);
            label.setText(getNameField());
            nameField.setEditable(false);
        }
    }

    public String getNameField() {
        return nameField.getText();
    }

    public Boolean isEditing() {
        return nameField.isVisible();
    }
}
