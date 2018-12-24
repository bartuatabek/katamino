import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
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

    private int[][] currentBoard;

    private Color currentColor;


    /* ************For blocks not yet implemented*************
    //Setting the image pattern
    String link = "http://4.bp.blogspot.com/-k5rNBSKgLSM"
            + "/UBQJotL5UoI/AAAAAAAAB50/Y_88LwoLGf4/s320"
            + "/Seamless+floor+concrete+stone+block+tiles+texture.jpg";
    private Image image = new Image(link);
    ImagePattern radialGradient = new ImagePattern(image, 20, 20, 40, 40, false);
    **********************************************************/
    private boolean blocked = false;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newKataminoButton.setButtonName("Next Katamino");
        confirmButton.setButtonName("Confirm");
        blockButton.setButtonName("Block");
        currentBoard = new int[11][22];
        currentColor =  Color.color(Math.random(),Math.random(),Math.random());
        currentPentominoID = 1;

        for (Node node : gameTilePane.getChildren())
            if (node instanceof KataminoDragCell)
                    node.setOnMouseClicked(colorTile);
    }

    private EventHandler<MouseEvent> colorTile = event ->  {
        KataminoDragCell cell = (KataminoDragCell)event.getSource();
        int count = gameTilePane.getChildren().indexOf(cell);
        if(blocked) {
            cell.setBlocked(true);
            currentBoard[count/22][count%22] = -2;
        }
        else
            currentBoard[count/22][count%22] = currentPentominoID;
        cell.setCellColor(currentColor);

        /*
        System.out.println("blocked:" + blocked);
        for (int i = 0 ; i < 11; i++) {
            for (int j = 0 ; j < 22 ; j++)
                System.out.print(currentBoard[i][j] + " ");
            System.out.println();
        }*/
    };

    @FXML
    public void nextKataminoClicked()  {
        blocked = false;
        currentColor =  Color.color(Math.random(),Math.random(),Math.random());
        currentPentominoID++;
        System.out.println("NEXT KATAMINO");
    }

    @FXML
    public void blockButtonClicked() {
        currentColor = Color.BLACK;
        blocked = true;
    }

    @FXML
    public void confirmButtonClicked() throws IOException{
        // Save the level
        // Pop up?
        // Return to which screen
        System.out.println("CONFIRM");

        FileManager fm = new FileManager();
        fm.saveCustomBoard(currentBoard, "board1");
    }

    @FXML
    public void backButtonClicked() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("modeSelectionMenu.fxml"));
        root.getChildren().setAll(pane);
    }

}

