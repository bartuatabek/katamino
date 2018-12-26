import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import kataminoBackButton.KataminoBackButton;
import kataminoButton.KataminoButton;
import kataminoDragCell.KataminoDragCell;

import java.io.IOException;
import java.net.URL;

import java.util.ArrayList;
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

    @FXML protected  KataminoButton undoButton;

    private int currentPentominoID;

    private int[][] currentBoard;

    private Color currentColor;
    private boolean blocked = false;
    private boolean empty;
    private boolean newClicked;
    private int count;
    private KataminoDragCell cell;


    private ArrayList<Color> colorList = new ArrayList<Color>() {{
        add(Color.web("FF3B30"));
        add(Color.web("FF9500"));
        add(Color.web("FFF700"));
        add(Color.web("4CD964"));
        add(Color.web("2D7D7D"));
        add(Color.web("007AFF"));
        add(Color.web("5856D6"));
        add(Color.web("9856D6"));
        add(Color.web("8B572A"));
        add(Color.web("B8E986"));
        add(Color.web("BD10E0"));
        add(Color.web("FFBB99"));
    }};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newKataminoButton.setButtonName("Next");
        confirmButton.setButtonName("Confirm");
        blockButton.setButtonName("Block");
        undoButton.setButtonName("Undo");
        currentPentominoID = 0;
        currentColor = colorList.get(0);
        currentBoard = new int[11][22];
        empty = true;
        newClicked = false;
        count = -1;

        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 22; j++) {
                currentBoard[i][j] = -1;
            }
        }

        for (Node node : gameTilePane.getChildren())
            if (node instanceof KataminoDragCell)
                    node.setOnMouseClicked(colorTile);
    }

    private EventHandler<MouseEvent> colorTile = event ->  {
        System.out.println("Start: " + currentPentominoID);
        cell = (KataminoDragCell)event.getSource();
        count = gameTilePane.getChildren().indexOf(cell);
        boolean disconnected = false;
        boolean newKatamino = false;

        final int up_count = count - 22, left_count = count - 1, right_count = count + 1, down_count = count + 22;
        if(empty) {
            empty = false;
        }
        else {
            disconnected = true;
            if(count>=22) {
                if (currentBoard[up_count / 22][up_count % 22] != -1)
                    disconnected = false;
            }
            if(count>=1) {
                if (disconnected && currentBoard[left_count / 22][left_count % 22] != -1)
                    disconnected = false;
            }
            if(count<=(11*22-1)) {
                if (disconnected && currentBoard[right_count / 22][right_count % 22] != -1)
                    disconnected = false;
            }
            if(count<=(11*22-22)) {
                if (disconnected && currentBoard[down_count / 22][down_count % 22] != -1)
                    disconnected = false;
            }
        }
        if (!newClicked) {
            newKatamino = true;
            if(count>=22) {
                if(currentBoard[up_count/22][up_count%22] == currentPentominoID)
                    newKatamino = false;
            }
            if(count>=1) {
                if(newKatamino && currentBoard[left_count/22][left_count%22] == currentPentominoID)
                    newKatamino = false;
            }
            if(count<=(11*22-1)) {
                if(newKatamino && currentBoard[right_count/22][right_count%22] ==currentPentominoID)
                    newKatamino = false;
            }
            if(count<=(11*22-22)) {
                if(newKatamino && currentBoard[down_count/22][down_count%22] == currentPentominoID)
                    newKatamino = false;
            }
        }
        System.out.println( "disconnected: " + disconnected);
        System.out.println( "blocked: " + blocked);
        System.out.println( "newKatamino: " + newKatamino);
        if(!disconnected) {
            if(!blocked && newKatamino) {
                nextKataminoClicked();
            }
            if(!blocked)
                currentBoard[count / 22][count % 22] = currentPentominoID;
            else {
                cell.setBlocked(true);
                currentBoard[count/22][count%22] = -2;
            }

            cell.setCellColor(currentColor);


            System.out.println("----------------------------");
            for (int i = 0; i < 11; i++) {
                for (int j = 0; j < 22; j++) {
                    System.out.print(currentBoard[i][j] + " ");
                }
                System.out.println();
            }
        }
        newClicked = false;
    };

    @FXML
    public void undoClicked() {
        currentBoard[count/22][count%22] = -1;
        cell.setCellColor(Color.web("#262626"));
    }
    @FXML
    public void nextKataminoClicked()  {
        blocked = false;
        currentPentominoID++;
        currentColor = colorList.get((currentPentominoID-1)%(colorList.size()));
        newClicked = true;
    }

    @FXML
    public void blockButtonClicked() {
        currentColor = Color.BLACK; // -2??
        blocked = true;
    }

    @FXML
    public void confirmButtonClicked() throws IOException{
        // Save the level
        // Pop up?
        // Return to which screen
        System.out.println("CONFIRM");

        FileManager fm = new FileManager();
        fm.saveCustomBoard(currentBoard);
    }

    @FXML
    public void backButtonClicked(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("customMenu.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        stage.setWidth(750);
        stage.setHeight(450);

        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        root.getChildren().setAll(pane);
    }

}

