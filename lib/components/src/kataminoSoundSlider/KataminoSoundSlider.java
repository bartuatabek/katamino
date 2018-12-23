package kataminoSoundSlider;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class KataminoSoundSlider extends Pane {
    @FXML private ImageView horizontalBar;
    @FXML private ImageView verticalBar;
    private double firstPosition;

    public KataminoSoundSlider() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("kataminoSoundSlider.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
        verticalBar.setOnMouseDragEntered(new EventHandler<MouseDragEvent>() {
            @Override
            public void handle(MouseDragEvent event) {
                firstPosition = event.getX();
            }
        });
        verticalBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(horizontalBar.getX() <= firstPosition + event.getX() && horizontalBar.getFitWidth()+horizontalBar.getX() >=firstPosition + event.getX())
                    verticalBar.setX(firstPosition + event.getX());
                getRelativeLocation();
            }
        });
    }
    public double getRelativeLocation() {
        return 100 * (verticalBar.getX() - horizontalBar.getX())/horizontalBar.getFitWidth();
    }
}
