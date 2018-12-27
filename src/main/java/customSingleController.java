import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import kataminoDragCell.KataminoDragCell;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class customSingleController extends SinglePlayerGameController {
 @Override
 public void initialize(URL location, ResourceBundle resources) {
     super.initialize(location,resources);
     levelButton.setButtonName("Boards");
     EventHandler eventHandler = new EventHandler<MouseEvent>() {
         @Override
         public void handle(MouseEvent event) {
             if (gridStack.isVisible()) {
                 KataminoDragCell[][] temp= new KataminoDragCell[11][22];
                 try {
                     int rowNode = 0;
                     int colNode = 0;
                     for (Node node : gameTilePane.getChildren()) {
                         if (node instanceof KataminoDragCell) {
                             if (node.getBoundsInParent().contains(event.getX(), event.getY())) {
                                 Integer[] location = findLocationTilePane(node, gameTilePane);
                                 rowNode = location[0];
                                 colNode = location[1];
                             }
                         }
                     }
                     for (int o = 0; o < 11; o++)
                     {
                         for (int k = 0; k < 22; k++){
                             temp[o][k]=(KataminoDragCell) gameTilePane.getChildren().get(o * 22 + k);
                         }
                     }
                     game.getGameBoard().setGrid(temp);
                     if(((SinglePlayerGame)game).getPlayer() != null&&((((SinglePlayerGame)game).getPlayer().getAccessibleLevel())==(((SinglePlayerGame)game).getCurrentLevel()) ))
                         ( (SinglePlayerGame)game).savePlayerBoard();
                     gridStack.setVisible(clashCheck(rowNode, colNode));
                 } catch (Exception e) {
                     System.out.println(e);
                 }

                 if(isFull()){
                     stopwatchLabel.setText("You Won!!");
                     pauseGame();
                 }
             }
         }
     };
     gameTilePane.setOnMouseReleased(eventHandler);
     gridStack.setOnMouseReleased(eventHandler);
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
