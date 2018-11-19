/** Game controller controls the game board logic and tile actions.
 * @author Bartu Atabek
 * @version 1.0
 */

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.Stack;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
//import javafx.scene.layout.ColumnConstraints;
//import javafx.scene.layout.RowConstraints;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import kataminoDragCell.KataminoDragCell;

public class GameController implements Initializable {

    private Stopwatch stopwatch;
    private int count;
    private Boolean isPaused;
    private GameBoard board;

    @FXML
    private GridPane gameGridPane;

    @FXML
    private Label stopwatchLabel;

    @FXML
    private Label playerLabel;

//    public GameController(Level level){
//        board= new GameBoard(level);
//    }

    public boolean isFull(){
            return false;
    }

    public void placePentomino(){}

    public void movePentomino (){}

    public boolean clashCheck(){
        int pentominoID = 0;
        if( board.getGrid()[0][0].getPentominoInstanceID() == pentominoID) //wrong
            return true;
        return false;
    }

    public void drawBorder(){

    }

    public void colorClashingCells(){

    }

    Timeline stopwatchChecker;

    public void updateStopwatch() {
        stopwatchChecker = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            stopwatchLabel.setText(secondsToString(count));
            count++;
        }));
        stopwatchChecker.setCycleCount(Timeline.INDEFINITE);
        stopwatchChecker.play();
    }

    public void startGame(){
        updateStopwatch();
    }

    public void pauseGame() {
        stopwatchLabel.setText("▶️" + stopwatchLabel.getText());
        stopwatchChecker.stop();
    }

    public void playPause(MouseEvent e) {
        if (isPaused) {
            isPaused = !isPaused;
            startGame();
        } else {
            isPaused = !isPaused;
            pauseGame();
        }
    }
    public int[][] findSiblings(MouseEvent event){
        ArrayList<Node> oldNodes = new ArrayList<>();
        Node source = (Node) event.getSource();
        KataminoDragCell currentPentomino;
        int pentominoInstanceID = ((KataminoDragCell) source).getPentominoInstanceID();
        ObservableList<Node> cells = gameGridPane.getChildren();
        Stack<Node> currentSearch = new Stack<>();
        int[][] currentShape= new int[11][22];
        currentSearch.push(source);
        Integer colIndex;
        Integer rowIndex;

        while(!currentSearch.empty()) {
            colIndex = GridPane.getColumnIndex(currentSearch.peek());
            rowIndex = GridPane.getRowIndex(currentSearch.peek());
            if(colIndex == null)
                colIndex = 0;
            if(rowIndex == null)
                rowIndex = 0;
            currentShape[rowIndex][colIndex] = pentominoInstanceID;
            oldNodes.add(currentSearch.pop());

            if(colIndex + 1 <= 21)
            {
                currentPentomino = (KataminoDragCell)cells.get(rowIndex*22+colIndex + 1);
                if(!oldNodes.contains(currentPentomino) && currentPentomino.getPentominoInstanceID() == pentominoInstanceID)
                    currentSearch.push(currentPentomino);
            }

            if(colIndex - 1 >= 0)
            {
                currentPentomino = (KataminoDragCell)cells.get(rowIndex*22+colIndex - 1);
                if(!oldNodes.contains(currentPentomino) &&currentPentomino.getPentominoInstanceID() == pentominoInstanceID)
                    currentSearch.push(currentPentomino);
            }
            if(rowIndex + 1 <= 10)
            {
                currentPentomino = (KataminoDragCell)cells.get((rowIndex + 1)*22+colIndex);
                if(!oldNodes.contains(currentPentomino) &&currentPentomino.getPentominoInstanceID() == pentominoInstanceID)
                    currentSearch.push(currentPentomino);
            }
            if(rowIndex - 1 >= 0)
            {
                currentPentomino = (KataminoDragCell)cells.get((rowIndex - 1)*22+colIndex);
                if(!oldNodes.contains(currentPentomino) &&currentPentomino.getPentominoInstanceID() == pentominoInstanceID)
                    currentSearch.push(currentPentomino);
            }
        }
        return currentShape;
    }


    public String secondsToString(int pTime) {
        return String.format("%02d:%02d", pTime / 60, pTime % 60);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        final int numCols = 20 ;
//        final int numRows = 20 ;
//        for (int i = 0; i < numCols; i++) {
//            ColumnConstraints colConst = new ColumnConstraints();
//            colConst.setPercentWidth(100.0 / numCols);
//            gameGridPane.getColumnConstraints().add(colConst);
//        }
//        for (int i = 0; i < numRows; i++) {
//            RowConstraints rowConst = new RowConstraints();
//            rowConst.setPercentHeight(100.0 / numRows);
//            gameGridPane.getRowConstraints().add(rowConst);
//        }
        count = 0;
        isPaused = false;
        startGame();

        System.out.println(gameGridPane.getChildren());
    }
}