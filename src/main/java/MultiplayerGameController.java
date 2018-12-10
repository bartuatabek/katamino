import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import kataminoDragBlock.KataminoDragBlock;
import kataminoDragCell.KataminoDragCell;

import java.net.URL;
import java.util.ResourceBundle;

public class MultiplayerGameController extends GameController {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        game = new MultiplayerGame();
    }
}
