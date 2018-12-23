import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import kataminoBackButton.KataminoBackButton;
import kataminoButton.KataminoButton;
import kataminoDragCell.KataminoDragCell;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;

public class BoardMakerController extends GameController implements Initializable  {

    @FXML AnchorPane root;

    @FXML
    protected TilePane gameTilePane;

    @FXML
    protected KataminoBackButton backButton;

    @FXML
    protected KataminoButton newKataminoButton;

    @FXML
    protected KataminoButton confirmButton;

    @FXML
    protected KataminoButton blockButton;

    private int currentPentominoID;

    private int [][] currentBoard;

    protected Color currentColor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newKataminoButton.setButtonName("Next Katamino");
        confirmButton.setButtonName("Confirm");
        blockButton.setButtonName("Block");
        currentBoard = new int[11][22];
        currentColor =  Color.color(Math.random(),Math.random(),Math.random());
        currentPentominoID = 1;

        gameTilePane.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    @FXML
    public void nextKataminoClicked()  {
        currentColor =  Color.color(Math.random(),Math.random(),Math.random());
        currentPentominoID++;
        System.out.println("NEXT KATAMINO");
    }

    @FXML
    public void blockButtonClicked() {
        currentColor = Color.WHITE;
        currentPentominoID = -2;
        System.out.println("BLOCK");
    }

    @FXML
    public void confirmButtonClicked() {
        // Save the level
        // Pop up?
        // Return to which screen
        System.out.println("CONFIRM");
    }

    @FXML
    public void backButtonClicked(MouseEvent e) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("modeSelectionMenu.fxml"));
        root.getChildren().setAll(pane);
    }

    private EventHandler<MouseEvent> eventHandler = event -> {
        int rowNode;
        int colNode;
        for (Node node : gameTilePane.getChildren()) {
            if (node instanceof KataminoDragCell) {
                if (node.getBoundsInParent().contains(event.getSceneX(), event.getSceneY())) {
                    Integer[] location1 = findLocationTilePane(node, gameTilePane);
                    if (location1 != null) {
                        System.out.println(location1[0]);
                        System.out.println(location1[1]);
                        rowNode = location1[0] - 1;
                        colNode = location1[1] - 4;

                        currentBoard[rowNode][colNode] = currentPentominoID;
                        KataminoDragCell clickedCell = (KataminoDragCell) gameTilePane.getChildren().get(rowNode * 22 + colNode);
                        clickedCell.setPentominoInstanceID(currentPentominoID);
                        clickedCell.setCellColor(Color.WHITE);//currentColor);
                        System.out.println(rowNode);
                        System.out.println(colNode);
                        break;
                    }
                    System.out.println("NAL");
                }
            }
        }

    };



}

