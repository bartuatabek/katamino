import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import kataminoLevelButton.KataminoLevelButton;
import kataminoLongButton.KataminoLongButton;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModeSelectionController implements Initializable {

    @FXML
    private KataminoLongButton kataminoArcadeButton;

    @FXML
    private KataminoLongButton kataminoCustomButton;

    @FXML
    private AnchorPane root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        kataminoArcadeButton.setButtonName("Classic(Arcade) Mode");
        kataminoCustomButton.setButtonName("Custom Shapes Mode");
    }

    @FXML
    public void arcadeSelected(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("playerSelection.fxml"));
        root.getChildren().setAll(pane);
    }

    @FXML
    public void customSelected(MouseEvent event) throws IOException {
        FXMLLoader customGameLoader = new FXMLLoader(getClass().getResource("singlePlayerGame.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        AnchorPane pane = customGameLoader.load();

        SinglePlayerGameController gameController = customGameLoader.getController();
        gameController.gameSetup(1);

        stage.setWidth(1250);
        stage.setHeight(700);

        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        root.getChildren().setAll(pane);
        root.getChildren().setAll(pane);
    }

    @FXML
    public void backButtonClicked(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        root.getChildren().setAll(pane);
    }
}
