import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import kataminoDragBlock.KataminoDragBlock;
import kataminoDragCell.KataminoDragCell;
import kataminoLongButton.KataminoLongButton;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SinglePlayerGameController extends GameController{
    @FXML
    AnchorPane pauseMenu;

    @FXML
    KataminoLongButton exitButton;

    @FXML
    KataminoLongButton levelButton;

    @FXML
    AnchorPane root;

    @FXML
    ImageView tutorial1;

    @FXML
    ImageView tutorial2;

    @FXML
    ImageView tutorial3;

    @FXML
    ImageView tutorial4;

    @FXML
    ImageView tutorial5;

    @FXML
    ImageView tutorial6;

    private int currentTutorial;

    private final Integer[][][] TUTORIAL_ARR = {null, {{3,9},{3,10},{3,11},{3,12},{4,12}}, {{4,9},{4,10},{4,11},{5,11},{5,12}}, {{5,10},{6,10},{6,11},{6,12},{7,12}}, {{5,9},{6,9},{7,9},{7,10},{7,11}}, null};

    public void loadLevel() throws FileNotFoundException {
        for (int i= 0; i < game.getGameBoard().getGrid().length; i++) {
            for (int j = 0; j < game.getGameBoard().getGrid()[0].length; j++) {
                KataminoDragCell currentCell = (KataminoDragCell) gameTilePane.getChildren().get((i*22)+j);
                KataminoDragCell temp = game.getGameBoard().getGrid()[i][j];
                currentCell.setBlocked(false);
                int cellId = temp.getPentominoInstanceID();
                currentCell.setPentominoInstanceID(temp.getPentominoInstanceID());
                currentCell.setOnBoard(temp.isOnBoard());
                currentCell.setCellColor(temp.getColor());
                if (cellId == 0)
                    currentCell.setBorderColor(Color.WHITE);
                currentCell.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        generatePreview(event);
                    }
                });
            }
        }
        tutorial6.setVisible(false);
        tutorial6.setDisable(true);
        animate(false, false);
    }

    public void loadOldBoard() throws FileNotFoundException {
        System.out.println("LOAD OLD BOARD");
        GameBoard  g=  new GameBoard( ((SinglePlayerGame)game).getPlayer().getAccessibleLevel());
        for (int i= 0; i <g.getGrid().length; i++) {
            for (int j = 0; j < g.getGrid()[0].length; j++) {
                KataminoDragCell currentCell = (KataminoDragCell) gameTilePane.getChildren().get((i*22)+j);
                KataminoDragCell temp = game.getGameBoard().getGrid()[i][j];
                currentCell.setBlocked(false);
                int cellId = temp.getPentominoInstanceID();
                currentCell.setPentominoInstanceID(temp.getPentominoInstanceID());
                currentCell.setOnBoard(temp.isOnBoard());
                currentCell.setCellColor(temp.getColor());
                if (cellId == 0)
                    currentCell.setBorderColor(Color.WHITE);
            }
        }
        GameBoard belongToElse= new GameBoard(((SinglePlayerGame)game).getPlayer().getLatestBoard());
        game.setGameBoard(belongToElse);
        for (int i= 0; i <belongToElse.getGrid().length; i++) {
            for (int j = 0; j < belongToElse.getGrid()[0].length; j++) {
                KataminoDragCell currentCell = (KataminoDragCell) gameTilePane.getChildren().get((i*22)+j);
                KataminoDragCell temp = belongToElse.getGrid()[i][j];
                currentCell.setBlocked(false);
                currentCell.setPentominoInstanceID(temp.getPentominoInstanceID());
                currentCell.setCellColor(temp.getColor());
                currentCell.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        generatePreview(event);
                    }
                });
            }
        }

        animate(false, false);
    }
    public void gameSetup(int level, int gameScore, Player player) {
        game = new SinglePlayerGame(level, player.getHighScore(), player);
        game.setStopwatch(new Stopwatch(player.getLatestTime()));
        playerLabel.setText(((SinglePlayerGame) game).getPlayer().getPlayerName() + " " + ((SinglePlayerGame) game).getPlayer().getHighScore());
        if(level == 1){
            currentTutorial = 0;
            tutorial1.setVisible(true);
            tutorial1.setDisable(false);
        }
        try {
            loadOldBoard();
        } catch (Exception e) {
            System.out.println(e);
        }
      //  startGame();
        updateStopwatch();
    }

    public void gameSetup(int level,Player player) {
        System.out.println(player);

        if(player == null) {
            playerLabel.setText("");
            game = new SinglePlayerGame(level);
        }
        else {
            playerLabel.setText(player.getPlayerName());
            game = new SinglePlayerGame(level,player);
        }

        try {
            loadLevel();
        } catch (Exception e) {
            System.out.println(e);
        }
        startGame();
    }

    public void nextTutorial(Event event)
    {
        if(event != null)
        {
            ((ImageView)event.getSource()).setVisible(false);
            ((ImageView)event.getSource()).setDisable(true);
            if(TUTORIAL_ARR[currentTutorial] == null)
            {
                nextTutorial(null);
                return;
            }
            for(int j = 0; j < TUTORIAL_ARR[currentTutorial].length;j++)
            {
                ((KataminoDragCell)gameTilePane.getChildren().get(TUTORIAL_ARR[currentTutorial][j][0] * 22 + TUTORIAL_ARR[currentTutorial][j][1])).setBorderColor(Color.WHITE);
            }
        }
        else
        {
            currentTutorial++;
            if(TUTORIAL_ARR[currentTutorial] != null)
            {
                for(int j = 0; j < TUTORIAL_ARR[currentTutorial].length;j++)
                {
                    ((KataminoDragCell)gameTilePane.getChildren().get(TUTORIAL_ARR[currentTutorial][j][0] * 22 + TUTORIAL_ARR[currentTutorial][j][1])).setBorderColor(Color.RED);
                }
                if(currentTutorial == 1) {
                    tutorial2.setVisible(true);
                    tutorial2.setDisable(false);
                }
                else if (currentTutorial == 2){
                    tutorial3.setVisible(true);
                    tutorial3.setDisable(false);
                }else if (currentTutorial == 3){
                    tutorial4.setVisible(true);
                    tutorial4.setDisable(false);
                }else if (currentTutorial == 4){
                    tutorial5.setVisible(true);
                    tutorial5.setDisable(false);
                }else if (currentTutorial == 5){
                    tutorial6.setVisible(true);
                    tutorial6.setDisable(false);
                }
            }
        }
    }

    @Override
    public void gameOverAction()  {
        if(((SinglePlayerGame) game).getPlayer() != null){
            ((SinglePlayerGame) game).incrementGameScore(99);
            playerLabel.setText(((SinglePlayerGame) game).getPlayer().getPlayerName() + " " + ((SinglePlayerGame) game).getPlayer().getHighScore());

           try {
               ((SinglePlayerGame) game).updateLevel();
           }catch (IOException o)
           { System.out.println(o);}



            try {
                loadLevel();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        animate(false, false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            kataminoDragCell = new KataminoDragCell();
            preview = new KataminoDragBlock();
            gridStack.setOnKeyPressed(keyPressed);
            gridStack.getChildren().add(preview);
            gridStack.setVisible(false);
        } catch (Exception e) {
            System.out.println(e);
        }
        exitButton.setButtonName("Exit");
        levelButton.setButtonName("Levels");
        pauseMenu.setOnMouseClicked(new EventHandler<MouseEvent>() { //not generic
            @Override
            public void handle(MouseEvent event) {
                for (Node node : timerPane.getChildren()) {
                    if(node instanceof VBox)
                    {
                        if (node.getBoundsInParent().contains(event.getSceneX(), event.getSceneY()) && ((VBox) node).getChildren().get(0) == stopwatchLabel) {
                            stopwatchLabel.fireEvent(event);
                        }
                    }
                }
            }
        });

        gameTilePane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                SinglePlayerGameController.super.getMousePos(event);
                preview.fireEvent(event);
            }
        });

        gameTilePane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                SinglePlayerGameController.super.getMousePos(event);
                preview.fireEvent(event);
            }
        });

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
                        SinglePlayerGameController.super.mouseCol = colNode;
                        SinglePlayerGameController.super.mouseRow = rowNode;
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
                    if(((SinglePlayerGame)game).getCurrentLevel() == 1)
                    {
                        boolean goNext = true;
                        if(currentTutorial != 6 && TUTORIAL_ARR[currentTutorial] != null)
                        {
                            label:
                            for(int j = 0; j < TUTORIAL_ARR[currentTutorial].length;j++)
                            {
                                KataminoDragCell dragCell = ((KataminoDragCell)gameTilePane.getChildren().get(TUTORIAL_ARR[currentTutorial][j][0] * 22 + TUTORIAL_ARR[currentTutorial][j][1]));
                                if(dragCell.getPentominoInstanceID() == 0 || dragCell.getPentominoInstanceID() == -1)
                                {
                                    goNext = false;
                                    break label;
                                }
                            }
                        }
                        if(goNext)
                            nextTutorial(null);
                    }
                    if(isFull()){
                        stopwatchLabel.setText("You Won!!");
                    }
                }
            }
        };
        gameTilePane.setOnMouseReleased(eventHandler);
        gridStack.setOnMouseReleased(eventHandler);
    }
    public void resumeGame() {
        super.resumeGame();
        pauseMenu.setVisible(false);
        pauseMenu.setDisable(true);
        animate(false,false);
    }

    public void pauseGame() {
        super.pauseGame();
        pauseMenu.setVisible(true);
        pauseMenu.setDisable(false);
        animate(true,false);
    }
    @FXML
    public void exitButtonClicked(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
    //    ( ( (SinglePlayerGame)game).getPlayer()).setLatestBoard(((SinglePlayerGame)game).getGameBoard().getBoard(),game.getElapsedSeconds());
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
         ( (SinglePlayerGame)game).savePlayerBoard();
        stage.setWidth(750);
        stage.setHeight(500);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        root.getChildren().setAll(pane);

    }

    @FXML
    public void levelButtonClicked(MouseEvent event) throws IOException {
        FXMLLoader levelMenuLoader = new FXMLLoader(getClass().getResource("levelMenu.fxml"));
        AnchorPane pane = levelMenuLoader.load();
        ( (SinglePlayerGame)game).savePlayerBoard();
        LevelMenuController lvlctrl = levelMenuLoader.getController();
        lvlctrl.setPlayer(((SinglePlayerGame)game).getPlayer());
        lvlctrl.updateLevelAccess();
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setWidth(750);
        stage.setHeight(500);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        root.getChildren().setAll(pane);
    }
}
