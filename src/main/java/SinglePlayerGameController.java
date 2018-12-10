import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import kataminoDragBlock.KataminoDragBlock;
import kataminoDragCell.KataminoDragCell;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class SinglePlayerGameController extends GameController{


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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        game = new SinglePlayerGame(1,0,new Player(0,2,"zey",0) ); ///playerrrrrrrrrrrrrrrrrrrrrrr

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
        gameGridPane.setOnMouseReleased(eventHandler);
        gridStack.setOnMouseReleased(eventHandler);
    }
}
