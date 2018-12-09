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

    public void updateStopwatch() {
        Timeline stopwatchChecker = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if(!game.isStopped()) {
                stopwatchLabel.setText(String.valueOf(String.format("%02d:%02d", game.getElapsedSeconds() / 60, game.getElapsedSeconds() % 60)));
            }
        }));
        stopwatchChecker.setCycleCount(Timeline.INDEFINITE);
        stopwatchChecker.play();
    }

    public void startGame(){
        game.startStopWatch();
        updateStopwatch();
    }

    public void pauseGame() {
        stopwatchLabel.setText("▶️" + stopwatchLabel.getText());
        game.pause();
    }

    public void resumeGame(){
        game.resume();
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
        if (game.isStopped()) {
            resumeGame();
        } else {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        game = new Game(1,0,new Player(0,2,"zey",0) ); ///playerrrrrrrrrrrrrrrrrrrrrrr

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