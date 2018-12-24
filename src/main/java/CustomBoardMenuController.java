import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import kataminoBackButton.KataminoBackButton;
import kataminoBoardButton.KataminoBoardButton;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomBoardMenuController implements Initializable {

    @FXML
    private KataminoBackButton backButton;

    @FXML
    private GridPane gridPane;

    @FXML
    private AnchorPane root;

    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i < gridPane.getChildren().size();i++) {
            ((KataminoBoardButton)gridPane.getChildren().get(i)).setBoardButton(i);
            gridPane.getChildren().get(i).setOnMouseClicked( new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    try {
                        boardButtonClicked(event);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            });
        }

    }

    @FXML
    public void backButtonClicked(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("customMenu.fxml"));
        root.getChildren().setAll(pane);
    }

    @FXML
    public void boardButtonClicked(MouseEvent event) throws IOException {
        FXMLLoader customGameLoader = new FXMLLoader(getClass().getResource("singlePlayerGame.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        AnchorPane pane = customGameLoader.load();
        SinglePlayerGameController gameController = customGameLoader.getController();
        int boardNo=  ((KataminoBoardButton)(event.getSource())).getButtonId();
        gameController.gameSetup(boardNo+1,null);
        System.out.println((((KataminoBoardButton)event.getSource()).getId()));
        stage.setWidth(1250);
        stage.setHeight(750);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);

        root.getChildren().setAll(pane);
    }



}