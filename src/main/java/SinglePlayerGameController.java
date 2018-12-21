import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import kataminoDragBlock.KataminoDragBlock;
import kataminoDragCell.KataminoDragCell;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class SinglePlayerGameController extends GameController{

    public void loadLevel() throws FileNotFoundException {
        for (int i= 0; i < game.getGameBoard().getGrid().length; i++) {
            for (int j = 0; j < game.getGameBoard().getGrid()[0].length; j++) {
                KataminoDragCell currentCell = (KataminoDragCell) gameTilePane.getChildren().get((i*22)+j);
                KataminoDragCell temp = game.getGameBoard().getGrid()[i][j];

                int cellId = temp.getPentominoInstanceID();
                currentCell.customizeCell(cellId, temp.isOnBoard(), temp.getColor());
                if (cellId == 0)
                    currentCell.setBorderColor(Color.WHITE);
                currentCell.setOnMousePressed( new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        generatePreview(event);
                    }
                });
            }
        }
        animate(false);
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

        animate(false);
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
}
