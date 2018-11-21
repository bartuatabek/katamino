/** Game controller controls the game board logic and tile actions.
 * @author Bartu Atabek
 * @version 1.0
 */

import java.net.URL;
import java.util.*;
import java.io.FileNotFoundException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import kataminoDragBlock.KataminoDragBlock;
import kataminoDragCell.KataminoDragCell;

public class GameController implements Initializable {

    private ArrayList<Color> colorList = new ArrayList<Color>(){{
        add(Color.ANTIQUEWHITE);
        add(Color.GRAY);
        add(Color.ALICEBLUE);
        add(Color.AZURE);
        add(Color.SALMON);
        add(Color.CHARTREUSE);
        add(Color.CORNFLOWERBLUE);
        add(Color.MEDIUMPURPLE);
        add(Color.LIME);
        add(Color.TEAL);
        add(Color.OLIVE);
        add(Color.ORANGERED);
    }};
    private static final int CELL_HEIGHT = 53;//TODO
    private static final int CELL_WIDTH = 56;//TODO

    private Level currentLevel;
    private Boolean isPaused;
    private int count;
    private KataminoDragCell kataminoDragCell;
    private int currentPentominoId;
    private int currentNodeRow;
    private int currentNodeCol;
    private ArrayList<ArrayList<Integer>> coordinateArr;

    @FXML
    private GridPane timerPane;

    @FXML
    private GridPane gameGridPane;

    @FXML
    private Label stopwatchLabel;

    @FXML
    private Label playerLabel;

    @FXML
    private AnchorPane gridStack;

    public boolean isFull(){
        for(int i = 3; i<=7;i++){
            for(int j = 5; j<=16;j++){
                KataminoDragCell currentCell = (KataminoDragCell) gameGridPane.getChildren().get((i * 22) + j);
                if (currentCell.getPentominoInstanceID() == 0){
                    return false;
                }
            }
        }
        return true;
    }

    public void placePentomino() {}

    public void movePentomino () {}

    public boolean clashCheck() {
        for (int i = 0; i < coordinateArr.size(); i++) {
            KataminoDragCell currentCell = (KataminoDragCell) gameGridPane.getChildren().get((coordinateArr.get(i).get(0) * 22) + coordinateArr.get(i).get(1));
            if (currentCell.getPentominoInstanceID() != 0){
                return true;
            }
        }

//        int pentominoID = 0;
//        if( board.getGrid()[0][0].getPentominoInstanceID() == pentominoID) //wrong
//            return true;
        return false;
    }

    public void colorClashingCells(){}

    KataminoDragBlock preview;

    public void generatePreview(MouseEvent e) {
        if(((KataminoDragCell) e.getSource()).getPentominoInstanceID() > 0 && !gridStack.isVisible())
        {
            try {
                Integer row = GridPane.getRowIndex((KataminoDragCell) e.getSource());
                Integer col = GridPane.getColumnIndex((KataminoDragCell) e.getSource());
                if(row == null){
                    currentNodeRow = 0;
                }
                else {
                    currentNodeRow = row;
                }
                if(col == null){
                    currentNodeCol = 0;
                }
                else {
                    currentNodeCol = col;
                }
                preview.relocate(0,0);
                int[][] children = findSiblings((Node) e.getSource());
                for (int i= 0; i < children.length; i++) {
                    for (int j = 0; j < children[0].length; j++) {
                        KataminoDragCell currentCell = (KataminoDragCell) preview.getChildren().get((i*22)+j);
                        KataminoDragCell currentTileCell = (KataminoDragCell) gameGridPane.getChildren().get((i*22)+j);
                        if (children[i][j] == 0) {
                            currentCell.setCellColor(Color.TRANSPARENT);
                            currentCell.setBorderColor(Color.TRANSPARENT);
                        } else {
                            currentTileCell.setCellColor(Color.web("#262626"));
                            currentTileCell.setPentominoInstanceID(-1);
                        }
                    }
                }

                preview.setPentomino(children);
                preview.setOpacity(0.5);
                preview.setLayoutY(preview.getLayoutY() + timerPane.getHeight());
                gridStack.setVisible(true);
                gridStack.getScene().setOnKeyPressed(keyPressed);
                System.out.println(gridStack.isVisible());
                System.out.println(preview.getLayoutX() + " - " + preview.getLayoutY());
            } catch (Exception exp) {
                System.out.println(exp);
            }
        }
    }

    public void loadLevel() throws FileNotFoundException {
       currentLevel = new FileManager().loadLevels();
       Integer[][] board = currentLevel.getBoard();

        for (int i= 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                KataminoDragCell currentCell = (KataminoDragCell) gameGridPane.getChildren().get((i*22)+j);
                Color cellColor = ((board[i][j] % 12) >= 0 && (board[i][j] != 0)) ? colorList.get(board[i][j] % 12) : Color.web("#262626");
                currentCell.customizeCell(board[i][j], board[i][j] != 0, cellColor);

                if (currentCell.getPentominoInstanceID() == 0) {
                    currentCell.setBorderColor(Color.WHITE);
                }
            }
        }
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

    public int[][] pentominoTransform(KeyEvent e){

        int grid[][] = new int [11][22];
        int transformedArray[][];
        int smllsRow = 1000;
        int smllsCol = 1000;
        int bgstCol = -1;
        int bgstRow = -1;
        for (ArrayList coord:coordinateArr)
        {
            if((int)coord.get(0) < smllsRow){
                smllsRow = (int)coord.get(0);
            }
            if((int)coord.get(1) < smllsCol){
                smllsCol = (int)coord.get(1);
            }
            if((int)coord.get(1) > bgstCol){
                bgstCol = (int)coord.get(1);
            }
            if((int)coord.get(0) > bgstRow){
                bgstRow = (int)coord.get(0);
            }
        }
        int width = bgstCol-smllsCol+1;
        int height = bgstRow-smllsRow+1;
        int[][] pentomino = new int[height][width];

        for(ArrayList coord:coordinateArr){
            pentomino[(int)coord.get(0)-smllsRow][(int)coord.get(1)-smllsCol] = currentPentominoId;
        }
        for( int i = 0; i < pentomino.length;i++){
            for(int j = 0; j < pentomino[0].length;j++){
                System.out.print(pentomino[i][j]);
            }
            System.out.println();
        }
        System.out.println("**********");

        transformedArray = pentomino;

        switch (e.getCode()) {
            case W: transformedArray =flipVertically(pentomino); break;
            case S: transformedArray =flipVertically(pentomino);  break;
            case A: transformedArray =rotateLeft(pentomino);  break;
            case D: transformedArray =rotateRight(pentomino);  break;
        }

        for( int i = 0; i < transformedArray.length;i++){
            for(int j = 0; j < transformedArray[0].length;j++){
                if(transformedArray[i][j] == currentPentominoId){
                    grid[i + smllsRow][j + smllsCol] = currentPentominoId;
                }
            }
        }
        for( int i = 0; i < transformedArray.length;i++){
            for(int j = 0; j < transformedArray[0].length;j++){
                System.out.print(transformedArray[i][j]);
            }
            System.out.println();

        }
        System.out.println("**********");
        coordinateArr.clear();
        for( int i = 0; i < transformedArray.length;i++){
            for(int j = 0; j < transformedArray[0].length;j++){
                if(transformedArray[i][j] == currentPentominoId) {
                    ArrayList<Integer> coord = new ArrayList<>();
                    Integer newCoordx = i + smllsRow;
                    Integer newCoordy = j + smllsCol;
                    coord.add(newCoordx);
                    coord.add(newCoordy);
                    coordinateArr.add(coord);
                }
            }
        }
        return grid;
    }

    public int[][] rotateRight(int[][] pentomino) {
        pentomino = transposeMatrix(pentomino);
        return flipHorizontally(pentomino);
    }

    public int[][] rotateLeft(int[][] pentomino) {
        pentomino = flipHorizontally(pentomino); // Why intelliJ gives a warning here?
        return transposeMatrix(pentomino);
    }

    public int[][] flipVertically(int[][] matrix){
        for(int col = 0;col < matrix[0].length; col++){
            for(int row = 0; row < matrix.length/2; row++) {
                int temp = matrix[row][col];
                matrix[row][col] = matrix[matrix.length - row - 1][col];
                matrix[matrix.length - row - 1][col] = temp;
            }
        }
        return matrix;
    }

    private int[][] transposeMatrix(int[][] matrix){
        int[][] temp = new int[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[0].length; j++)
                temp[j][i] = matrix[i][j];
        return temp;
    }
    private int[][] flipHorizontally(int[][] matrix){
        for(int j = 0; j < matrix.length; j++){
            for(int i = 0; i < matrix[j].length / 2; i++) {
                int temp = matrix[j][i];
                matrix[j][i] = matrix[j][matrix[j].length - i - 1];
                matrix[j][matrix[j].length - i - 1] = temp;
            }
        }
        return matrix;
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
    private EventHandler<KeyEvent> keyPressed = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            System.out.println("Hi");
            preview.setPentomino(pentominoTransform(event));
            event.consume();
        }
    };

    public int[][] findSiblings(Node source){
        ArrayList<Node> oldNodes = new ArrayList<>();
        KataminoDragCell currentPentomino;
        kataminoDragCell = (KataminoDragCell) source;
        currentPentominoId = kataminoDragCell.getPentominoInstanceID();
        int pentominoInstanceID = ((KataminoDragCell) source).getPentominoInstanceID();
        ObservableList<Node> cells = gameGridPane.getChildren();
        Stack<Node> currentSearch = new Stack<>();
        int[][] currentShape= new int[11][22];
        currentSearch.push(source);
        Integer colIndex;
        Integer rowIndex;
        coordinateArr = new ArrayList<>();
        while(!currentSearch.empty()) {
            colIndex = GridPane.getColumnIndex(currentSearch.peek());
            rowIndex = GridPane.getRowIndex(currentSearch.peek());
            if(colIndex == null)
                colIndex = 0;
            if(rowIndex == null)
                rowIndex = 0;
            currentShape[rowIndex][colIndex] = pentominoInstanceID;
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            arrayList.add(rowIndex);
            arrayList.add(colIndex);
            coordinateArr.add(arrayList);
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

        count = 0;
        isPaused = false;
        try {
            kataminoDragCell = new KataminoDragCell();
            loadLevel();
            preview = new KataminoDragBlock();
            gridStack.setOnKeyPressed(keyPressed);
            gridStack.getChildren().add(preview);
            gridStack.setVisible(false);
        } catch (Exception e) {
            System.out.println(e);

        }
        playerLabel.setText("Adamotu 0");
        startGame();
        gameGridPane.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {


            }
        });
        //gridStack.addEventHandler(EventType.ROOT, event ->gameGridPane.fireEvent(event));
        //gameGridPane.addEventHandler(EventType.ROOT, event -> gameGridPane.getChildren());
        gridStack.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int rowNode = 0;
                int colNode = 0;
                for( Node node: gameGridPane.getChildren()) {

                    if( node instanceof KataminoDragCell) {
                        if( node.getBoundsInParent().contains(event.getSceneX(),  event.getSceneY())) {
                            rowNode = (GridPane.getRowIndex( node) - 1);
                            colNode =  GridPane.getColumnIndex( node);
                        }
                    }
                }
                //KataminoDragCell currentCell = (KataminoDragCell) gameGridPane.getChildren().get((rowNode * 22) + colNode);

                Color cellColor = ((currentPentominoId % 12) >= 0 && (currentPentominoId != 0)) ? colorList.get(currentPentominoId % 12) : Color.web("#262626");
                //currentCell.customizeCell(currentPentominoId, kataminoDragCell.isOnBoard(), cellColor);
                int transferX = rowNode - coordinateArr.get(0).get(0);
                int transferY = colNode - coordinateArr.get(0).get(1);
                for (ArrayList<Integer> array: coordinateArr) {
                    array.set(0, array.get(0) + transferX);
                    array.set(1, array.get(1) + transferY);
                }
                for (int i = 0; i < coordinateArr.size(); i++) {
                    //TODO: boundary check and get cell location properly
                    KataminoDragCell currentCell = (KataminoDragCell) gameGridPane.getChildren().get((coordinateArr.get(i).get(0) * 22) + coordinateArr.get(i).get(1));
                    currentCell.setPentominoInstanceID(currentPentominoId);
                    currentCell.setCellColor(cellColor);
                }

                System.out.println(coordinateArr.get(0).get(0) + "   " + coordinateArr.get(0).get(1));
                System.out.println(rowNode + "   " + colNode);
                gridStack.setVisible(false);
            }
        });
    }
}