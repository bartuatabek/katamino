package kataminoBoardButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import java.io.IOException;

public class KataminoBoardButton extends Pane {

    @FXML
    ImageView boardImage1;

    @FXML
    ImageView boardImage2;

    @FXML
    ImageView boardImage3;

    @FXML
    ImageView boardImage4;

    private int id;

    public KataminoBoardButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("kataminoBoardButton.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }

    public void setBoardButton(int value) {
        if (value == 0) {
            id = 0;
            boardImage1.setVisible(true);
            boardImage2.setVisible(false);
            boardImage3.setVisible(false);
            boardImage4.setVisible(false);
        } else if (value == 1) {
            id = 1;
            boardImage1.setVisible(false);
            boardImage2.setVisible(true);
            boardImage3.setVisible(false);
            boardImage4.setVisible(false);
        } else if (value == 2) {
            id = 2;
            boardImage1.setVisible(false);
            boardImage2.setVisible(false);
            boardImage3.setVisible(true);
            boardImage4.setVisible(false);
        } else {
            id = value;
            boardImage1.setVisible(false);
            boardImage2.setVisible(false);
            boardImage3.setVisible(false);
            boardImage4.setVisible(true);
        }
    }

    public int getButtonId() {
        return id;
    }
}
