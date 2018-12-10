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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import kataminoDragBlock.KataminoDragBlock;
import kataminoDragCell.KataminoDragCell;

public class GameController implements Initializable {
    private Game game;
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
    private Level currentLevel;
    private Boolean isPaused;
    private int count;
    private KataminoDragCell kataminoDragCell;
    private int currentPentominoId;
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

    private KataminoDragBlock preview;

    public boolean isFull(){
        ObservableList<Node> cells = gameGridPane.getChildren();
        for(int i = 0; i < cells.size(); i ++)
        {

            KataminoDragCell currentPentomino = (KataminoDragCell)cells.get(i);

            if( currentPentomino.isOnBoard() && ((currentPentomino.getPentominoInstanceID() == -1) || (currentPentomino.getPentominoInstanceID() == 0)))
            {
                return false;
            }
        }
        return true;
    }

    public void placePentomino() {}

    public void movePentomino () {}

    public boolean clashCheck(int row, int col) {
        //TODO:border bugs
        ArrayList<KataminoDragCell> newLocationCells = new ArrayList<>();
        Color cellColor = ((currentPentominoId % 12) >= 0 && (currentPentominoId != 0)) ? colorList.get(currentPentominoId % 12) : Color.web("#262626");
        int transferX = row - coordinateArr.get(0).get(0);
        int transferY = col - coordinateArr.get(0).get(1);
        for (ArrayList<Integer> array: coordinateArr) {
            array.set(0, array.get(0) + transferX);
            array.set(1, array.get(1) + transferY);
        }
        int index;
        for (int i = 0; i < coordinateArr.size(); i++) {
            index = (coordinateArr.get(i).get(0) * 22) + coordinateArr.get(i).get(1);
            if(index >= 0)
            {
                KataminoDragCell currentPentomino = (KataminoDragCell) gameGridPane.getChildren().get(index);
                if(currentPentomino.getPentominoInstanceID() == -1 || currentPentomino.getPentominoInstanceID() == 0)
                {
                    newLocationCells.add(currentPentomino);
                }
                else {
                    ShakeTransition shakeTransition = new ShakeTransition(gridStack);
                    shakeTransition.playFromStart();
                    return true;
                }
            }
        }
        for(KataminoDragCell currentCell: newLocationCells)
        {
            currentCell.setPentominoInstanceID(currentPentominoId);
            currentCell.setCellColor(cellColor);
        }
        return false;
    }

    public void colorClashingCells(){}

    public void generatePreview(MouseEvent e) {
        if(((KataminoDragCell) e.getSource()).getPentominoInstanceID() > 0 && !gridStack.isVisible())
        {
            try {
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
            } catch (Exception exp) {
                System.out.println(exp);
            }
        }
    }

    public void loadLevel() throws FileNotFoundException {
        for (int i= 0; i < game.getGameBoard().getGrid().length; i++) {
            for (int j = 0; j < game.getGameBoard().getGrid()[0].length; j++) {
                KataminoDragCell temp =game.getGameBoard().getGrid()[i][j];
               temp.setOnMousePressed( new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        generatePreview(event);
                    }
                });
                gameGridPane.add(temp, j,i);
            }
        }
    }

   Timeline stopwatchChecker;

    public void updateStopwatch() {
        stopwatchChecker = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            stopwatchLabel.setText(String.valueOf( game.getElapsedSeconds()));

        }));
        stopwatchChecker.setCycleCount(Timeline.INDEFINITE);
        stopwatchChecker.play();
    }

    public void startGame(){
        game.startStopWatch();
        updateStopwatch();
       // updateStopwatch();


    }

    public void pauseGame() {
        stopwatchLabel.setText("▶️" + stopwatchLabel.getText());
        game.pause();
        /*stopwatchLabel.setText("▶️" + stopwatchLabel.getText());
        stopwatchChecker.stop();*/
    }

    public int[][] pentominoTransform(KeyEvent e){

        int grid[][] = new int [11][22];
        int smllsRow = 1000;
        int smllsCol = 1000;
        int bgstCol = -1;
        int bgstRow = -1;
        double origin_row, origin_col = -1;

        for (ArrayList coord:coordinateArr) {
            if ((int) coord.get(0) < smllsRow) {
                smllsRow = (int) coord.get(0);
            }
            if ((int) coord.get(1) < smllsCol) {
                smllsCol = (int) coord.get(1);
            }
            if ((int) coord.get(1) > bgstCol) {
                bgstCol = (int) coord.get(1);
            }
            if ((int) coord.get(0) > bgstRow) {
                bgstRow = (int) coord.get(0);
            }
        }

        int width = bgstCol-smllsCol+1;
        int height = bgstRow-smllsRow+1;

        switch (e.getCode()) {
            case W: flipVertically(width,  height, smllsRow, smllsCol ); break;
            case S: flipVertically(width,  height, smllsRow, smllsCol );  break;
            case A: rotateLeft( width,  height, smllsRow, smllsCol );  break;
            case D: rotateRight( width,  height, smllsRow, smllsCol);  break;
        }
        for (ArrayList<Integer>coord:coordinateArr)
        {
            grid[coord.get(0)][coord.get(1)]  = currentPentominoId;
        }
        return grid;
    }

    public void rotateRight(int width ,int height,int smllsRow, int smllsCol  ) {


        int origin_row = coordinateArr.get(0).get(0);
        int origin_col = coordinateArr.get(0).get(1);

        // move origin to 0,0
        for (ArrayList<Integer> coord : coordinateArr){
            int row = coord.get(0)-origin_row;
            int col = coord.get(1)-origin_col;
            int ncol =  -row+origin_col; // clockwise
            int nrow =   col+origin_row;
            coord.set(0,nrow);
            coord.set(1,ncol);
        }
    }

    public void rotateLeft(int width,  int height, int smllsRow, int smllsCol) {

        int origin_row = coordinateArr.get(0).get(0);
        int origin_col = coordinateArr.get(0).get(1);

        // move origin to 0,0
        for (ArrayList<Integer> coord : coordinateArr){
            int row = coord.get(0)-origin_row;
            int col = coord.get(1)-origin_col;
            int ncol = row+origin_col; // clockwise
            int nrow = -col+origin_row;
            coord.set(0,nrow);
            coord.set(1,ncol);
        }

    }

    public void flipVertically(int width,  int height, int smllsRow, int smllsCol ){
        int[][] pentomino = new int[height][width];

        for(ArrayList coord:coordinateArr){
            pentomino[(int)coord.get(0)-smllsRow][(int)coord.get(1)-smllsCol] = currentPentominoId;
        }
        for(int col = 0;col < pentomino[0].length; col++){
            for(int row = 0; row < pentomino.length/2; row++) {
                int temp = pentomino[row][col];
                pentomino[row][col] = pentomino[pentomino.length - row - 1][col];
                pentomino[pentomino.length - row - 1][col] = temp;
            }
        }
        coordinateArr.clear();
        for( int i = 0; i < pentomino.length;i++){
            for(int j = 0; j < pentomino[0].length;j++){
                if(pentomino[i][j] == currentPentominoId) {
                    ArrayList<Integer> coord = new ArrayList<>();
                    Integer newCoordx = i + smllsRow;
                    Integer newCoordy = j + smllsCol;
                    coord.add(newCoordx);
                    coord.add(newCoordy);
                    coordinateArr.add(coord);
                }
            }
        }
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
        int[][] currentShape= new int[11][22];///////////////////
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
        game = new Game(1,0,new Player(0,2,"zey") ); ///playerrrrrrrrrrrrrrrrrrrrrrr

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
        gameGridPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) { preview.fireEvent(event);
            }
        });
        gameGridPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                preview.fireEvent(event);
            }
        });


        EventHandler eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (gridStack.isVisible()) {
                    KataminoDragCell[][] temp= new KataminoDragCell[gameGridPane.impl_getRowCount()][gameGridPane.impl_getColumnCount()];
                    try {
                        int rowNode = 0;
                        int colNode = 0;
                        for (Node node : gameGridPane.getChildren()) {
                            if (node instanceof KataminoDragCell) {
                                if (node.getBoundsInParent().contains(event.getSceneX(), event.getSceneY())) {
                                    if (GridPane.getRowIndex(node) != null){
                                        rowNode = (GridPane.getRowIndex(node) - 1);
                                }
                                    if (GridPane.getColumnIndex(node) != null)
                                    {
                                        colNode = GridPane.getColumnIndex(node);
                                }
                                }
                            }
                        }
                        for(int o =0;o<gameGridPane.impl_getRowCount();o++)
                        {
                            for(int k =0;k<gameGridPane.impl_getColumnCount();k++){
                                temp[o][k]=(KataminoDragCell) gameGridPane.getChildren().get(o*22+k);
                            }
                        }
                        game.getGameBoard().setGrid(temp);
                        game.savePlayerBoard();
                        gridStack.setVisible(clashCheck(rowNode, colNode));
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    if (isFull()) {
                        pauseGame();
                        stopwatchLabel.setText("You Won!!");
                        playerLabel.setText("Adamotu 199");
                    }
                }
            }
        };
        gameGridPane.setOnMouseReleased(eventHandler);
        gridStack.setOnMouseReleased(eventHandler);
    }
}