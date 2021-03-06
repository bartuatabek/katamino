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
import javafx.scene.Parent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BoardMenuController implements Initializable {

    @FXML
    private KataminoBackButton backButton;

    @FXML
    private GridPane gridPane;

    @FXML
    private AnchorPane root;

    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i < gridPane.getChildren().size();i++) {
            ((KataminoBoardButton)gridPane.getChildren().get(i)).setBoardButton(i);
            gridPane.getChildren().get(i).setOnMouseClicked(event -> {
                try {
                    boardButtonClicked(event);
                } catch (Exception e) {
                    System.out.println(e);
                }
            });
        }

    }

    @FXML
    public void backButtonClicked(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        root.getChildren().setAll(pane);
    }

    @FXML
    public void boardButtonClicked(MouseEvent event) throws IOException {
        FXMLLoader multiGame = new FXMLLoader(getClass().getResource("multiplayerGame.fxml"));
        AnchorPane pane = multiGame.load();
        MultiplayerGameController gameController = multiGame.getController();

       // gameController.game=new MultiplayerGame(2);
       // AnchorPane pane = FXMLLoader.load(getClass().getResource("multiplayerGame.fxml"));

        //for later use Integer.parseInt(((KataminoBoardButton)event.getSource()).getId())
        int boardNo=  ((KataminoBoardButton)(event.getSource())).getButtonId();
        gameController.setBoardId(boardNo+4);

        gameController.startMulti();
       System.out.println((((KataminoBoardButton)event.getSource()).getId()));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setWidth(1250);
        stage.setHeight(720);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);

        root.getChildren().setAll(pane);
    }



}