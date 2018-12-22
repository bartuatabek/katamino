import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
        animate(false, false);
    }

    public void gameSetup(int level, int gameScore, Player player) {
        game = new SinglePlayerGame(level, player.getHighScore(), player);
        playerLabel.setText(((SinglePlayerGame) game).getPlayer().getPlayerName() + " " + ((SinglePlayerGame) game).getPlayer().getHighScore());
        try {
            loadLevel();
        } catch (Exception e) {
            System.out.println(e);
        }
        startGame();
    }
    public void gameSetup(int level) {
        game = new SinglePlayerGame(level);
        playerLabel.setText("");
        try {
            loadLevel();
        } catch (Exception e) {
            System.out.println(e);
        }
        startGame();
    }

    @Override
    public void gameOverAction() {
        if(((SinglePlayerGame) game).getPlayer() != null){
            ((SinglePlayerGame) game).updateLevel();
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
            public void handle(MouseEvent event) { preview.fireEvent(event);
            }
        });

        gameTilePane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
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
                                if (node.getBoundsInParent().contains(event.getSceneX(), event.getSceneY())) {
                                    Integer[] location = findLocationTilePane(node, gameTilePane);
                                    rowNode = location[0] - 1;
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
                        if(((SinglePlayerGame)game).getPlayer() != null)
                            ( (SinglePlayerGame)game).savePlayerBoard();
                        gridStack.setVisible(clashCheck(rowNode, colNode));
                    } catch (Exception e) {
                        System.out.println(e);
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

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setWidth(750);
        stage.setHeight(500);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        root.getChildren().setAll(pane);
    }

    @FXML
    public void levelButtonClicked(MouseEvent event) throws IOException { //TODO: Check for saved level
        FXMLLoader levelMenuLoader = new FXMLLoader(getClass().getResource("levelMenu.fxml"));
        AnchorPane pane = levelMenuLoader.load();
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
