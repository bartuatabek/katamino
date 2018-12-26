import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class customSingleController extends SinglePlayerGameController {
 @Override
 public void initialize(URL location, ResourceBundle resources) {
     super.initialize(location,resources);
     levelButton.setButtonName("Boards");
 }

    @Override
    @FXML
    public void exitButtonClicked(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setWidth(750);
        stage.setHeight(500);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        root.getChildren().setAll(pane);

    }

@FXML
public void boardButtonClicked(MouseEvent event) throws IOException {
    FXMLLoader levelMenuLoader = new FXMLLoader(getClass().getResource("customBoardMenu.fxml"));
    AnchorPane pane = levelMenuLoader.load();
    CustomBoardMenuController br = levelMenuLoader.getController();
    Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    stage.setWidth(750);
    stage.setHeight(500);
    Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
    stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
    stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    root.getChildren().setAll(pane);
}
}
