import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import kataminoButton.KataminoButton;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML KataminoButton levelMenuButton;
    @FXML KataminoButton highScoreMenuButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        levelMenuButton.setButtonName("Level Menu");
        highScoreMenuButton.setButtonName("Highscore Menu");

    }

    @FXML
    public void levelMenuButtonClicked(MouseEvent event) throws IOException {
        FXMLLoader levelMenuLoader = new FXMLLoader(getClass().getResource("levelMenu.fxml"));
        Parent levelPane = levelMenuLoader.load();
        Scene levelMenuScene = new Scene(levelPane);

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(levelMenuScene);
    }

    @FXML
    public void highScoreMenuButtonClicked(MouseEvent event) throws IOException {
        FXMLLoader highScoreLoader = new FXMLLoader(getClass().getResource("highScore.fxml"));
        Parent highScorePane = highScoreLoader.load();
        Scene highScoreScene = new Scene(highScorePane);

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(highScoreScene);
    }



}
