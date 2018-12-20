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
                currentCell.setPentominoInstanceID(cellId);
                if (cellId == 0)
                    currentCell.setBorderColor(Color.WHITE);
                currentCell.setCellColor(temp.getColor());
                currentCell.setOnBoard(temp.isOnBoard());
                currentCell.setOnMousePressed( new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        generatePreview(event);
                    }
                });
            }
        }
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

    @Override
    public void gameOverAction() {
        ((SinglePlayerGame) game).updateLevel();
        try {
            loadLevel();
        } catch (Exception e) {
            System.out.println(e);
        }

        for (Node n: gameTilePane.getChildren()) {
            RotateTransition rotator = new RotateTransition(Duration.millis(500), n);
            rotator.setFromAngle(120);
            rotator.setToAngle(0);
            rotator.setInterpolator(Interpolator.EASE_BOTH);
            rotator.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    resumeGame();
                }});
            rotator.play();
        }
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
                                    if (location[0] != null){
                                        rowNode = location[0] - 1;
                                    }
                                    if (location[1] != null)
                                    {
                                        colNode = location[1];
                                    }
                                }
                            }
                        }
                        for(int o =0;o<11;o++)
                        {
                            for(int k =0;k<22;k++){
                                temp[o][k]=(KataminoDragCell) gameTilePane.getChildren().get(o*22+k);
                            }
                        }
                        game.getGameBoard().setGrid(temp);
                        ( (SinglePlayerGame)game).savePlayerBoard();
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
        gameTilePane.setOnMouseReleased(eventHandler);
        gridStack.setOnMouseReleased(eventHandler);
    }
}
