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

    private int currentPentominoID;

    private int[][] currentBoard;

    private Color currentColor;
    private boolean blocked = false;

    protected ArrayList<Color> colorList = new ArrayList<Color>() {{
        add(Color.web("FF3B30"));
        add(Color.web("FF9500"));
        add(Color.web("FFCC00"));
        add(Color.web("4CD964"));
        add(Color.web("5AFAFA"));
        add(Color.web("007AFF"));
        add(Color.web("5856D6"));
        add(Color.web("FF2D55"));
        add(Color.web("8B572A"));
        add(Color.web("B8E986"));
        add(Color.web("BD10E0"));
        add(Color.web("FF5700"));
    }};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newKataminoButton.setButtonName("Next Katamino");
        confirmButton.setButtonName("Confirm");
        blockButton.setButtonName("Block");
        currentPentominoID = 0;
        currentColor = colorList.get(0);
        currentBoard = new int[11][22];

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
        KataminoDragCell cell = (KataminoDragCell)event.getSource();
        int count = gameTilePane.getChildren().indexOf(cell);

        if(!(currentPentominoID == 0 && currentBoard[count/22][count%22] == currentPentominoID)) {
            boolean disconnected = true;
            if (count>=23) {
                int up_left_count = count-23;
                if(currentBoard[up_left_count/22][up_left_count%22] == currentPentominoID)
                    disconnected = false;
            }
            if(count>=22 && disconnected) {
                int up_count = count-22;
                if(currentBoard[up_count/22][up_count%22] == currentPentominoID)
                    disconnected = false;
            }
            if(count>=21 && disconnected) {
                int up_right_count = count-21;
                if(currentBoard[up_right_count/22][up_right_count%22] == currentPentominoID)
                    disconnected = false;
            }
            if(count>=1 && disconnected) {
                int left_count = count-1;
                if(currentBoard[left_count/22][left_count%22] == currentPentominoID)
                    disconnected = false;
            }
            if(count<=(11*22-1) && disconnected) {
                int right_count = count+1;
                if(currentBoard[right_count/22][right_count%22] ==currentPentominoID)
                    disconnected = false;
            }
            if(count<=(11*22-21) && disconnected) {
                int down_left_count = count+21;
                if(currentBoard[down_left_count/22][down_left_count%22] == currentPentominoID)
                    disconnected = false;
            }
            if(count<=(11*22-22) && disconnected) {
                int down_count = count+22;
                if(currentBoard[down_count/22][down_count%22] == currentPentominoID)
                    disconnected = false;
            }
            if(count<=(11*22-23) && disconnected) {
                int down_right_count = count+23;
                if(currentBoard[down_right_count/22][down_right_count%22] == currentPentominoID)
                    disconnected = false;
            }
            if(disconnected) {
                nextKataminoClicked();
            }
            if(blocked) {
                cell.setBlocked(true);
                currentBoard[count/22][count%22] = -2;
            }
            else
                currentBoard[count/22][count%22] = currentPentominoID;
            cell.setCellColor(currentColor);

            System.out.println("----------------------------");
            for (int i = 0; i < 11; i++) {
                for (int j = 0; j < 22; j++) {
                    System.out.print(currentBoard[i][j] + " ");
                }
                System.out.println();
            }
    }
    };

    @FXML
    public void nextKataminoClicked()  {
        blocked = false;
        currentPentominoID++;
        currentColor = colorList.get((currentPentominoID-1)%(colorList.size()));
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
        fm.saveCustomBoard(currentBoard, "board1");
    }

    @FXML
    public void backButtonClicked(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("modeSelectionMenu.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        stage.setWidth(750);
        stage.setHeight(450);

        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        root.getChildren().setAll(pane);
    }

}

