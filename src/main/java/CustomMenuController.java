
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import kataminoLongButton.KataminoLongButton;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomMenuController implements Initializable{


    @FXML
    private KataminoLongButton playButton;

    @FXML
    private KataminoLongButton boardMakerButton;


    @FXML
    private AnchorPane root;

    FileManager fm = new FileManager();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playButton.setButtonName("Custom Boards");
        boardMakerButton.setButtonName("Board Maker");
        fm = new FileManager();

    }

    @FXML
    public void playSelected(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("boardMenu.fxml"));
        root.getChildren().setAll(pane);
    }

    @FXML
    public void boardMakerSelected(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("boardMaker.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        stage.setWidth(1250);
        stage.setHeight(720);

        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        root.getChildren().setAll(pane);
    }
    @FXML
    public void backButtonClicked() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("modeSelectionMenu.fxml"));
        root.getChildren().setAll(pane);
    }
}
