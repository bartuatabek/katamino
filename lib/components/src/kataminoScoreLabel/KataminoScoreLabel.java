package kataminoScoreLabel;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import java.io.IOException;

public class KataminoScoreLabel extends Label {

    @FXML
    private Label scoreLabel;

    public KataminoScoreLabel() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("kataminoScoreLabel.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }

    public void setScoreLabel(String text) {
        scoreLabel.setText(text);
    }

    public String getScoreLabel() {
        return scoreLabel.getText();
    }
}
