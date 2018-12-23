package kataminoBoardButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class KataminoBoardButton extends Pane {
    @FXML
    ImageView boardImage;

    public KataminoBoardButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("kataminoBoardButton.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }

    public ImageView getBoardImageView() {
        return boardImage;
    }

    public void setBoardImage(Image image) {
        boardImage.setImage(image);
    }
}
