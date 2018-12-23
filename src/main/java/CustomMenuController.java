import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.SpinnerValueFactory;
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

    ///////////////////////////////////////////////////////////
    private ObservableList<String> customBoards;
    private SpinnerValueFactory<String> valueFactory;
    private FileManager fm;
    ///////////////////////////////////////////////////////////

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playButton.setButtonName("Play with a Custom Board");
        boardMakerButton.setButtonName("Board Maker");
    }
    /*@FXML
    public void playSelected() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("singlePlayerGame.fxml"));
        root.getChildren().setAll(pane);
    }*/

    @FXML
    public void playSelected(MouseEvent event) throws IOException {
        FXMLLoader customGameLoader = new FXMLLoader(getClass().getResource("singlePlayerGame.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        AnchorPane pane = customGameLoader.load();

        SinglePlayerGameController gameController = customGameLoader.getController();
        gameController.gameSetup(1,null);

        stage.setWidth(1250);
        stage.setHeight(700);

        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        root.getChildren().setAll(pane);
        root.getChildren().setAll(pane);
    }

    @FXML
    public void boardMakerSelected(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("BoardMaker.fxml"));
        Scene mainMenuScene = new Scene(pane, 1450, 700);

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(mainMenuScene);

    }
    @FXML
    public void backButtonClicked(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("modeSelectionMenu.fxml"));
        root.getChildren().setAll(pane);
    }
}
