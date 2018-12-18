import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import kataminoBackButton.KataminoBackButton;
import kataminoButton.KataminoButton;
import kataminoChangeButton.KataminoChangeButton;
import kataminoDragBlock.KataminoDragBlock;
import kataminoDragCell.KataminoDragCell;
import kataminoLevelButton.KataminoLevelButton;
import kataminoLongButton.KataminoLongButton;
import kataminoPlayerAddButton.KataminoPlayerAddButton;
import kataminoSoundButton.KataminoSoundButton;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            AnchorPane root = new AnchorPane();
            root.setStyle("-fx-background-color: Salmon");
            root.getChildren().add(new KataminoButton());
            root.getChildren().add(new KataminoLevelButton());
            root.getChildren().add(new KataminoBackButton());

            KataminoDragBlock dragBlock = new KataminoDragBlock();
            int[][] pentomino = new int[][]{
                    { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
                    { -1, -1, -1, 6, 6, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
                    { -1, -1, -1, -1, 6, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
                    { -1, -1, -1, -1, 6, 6, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
                    { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
                    { -1, -1, -1, -1, -1, -1, -1, -1, -1, -2,-2, -2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
                    { -1, -1, -1, -1, -1, -1, -1, -1, -1, -2,-2, -2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
                    { -1, -1, -1, -1, -1, -1, -1, -1, -1, -2,-2, -2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
                    { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
                    { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 },
                    { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 }
            };
            dragBlock.setPentomino(pentomino);
            root.getChildren().add(dragBlock);

            root.getChildren().add(new KataminoSoundButton());
            root.getChildren().add(new KataminoPlayerAddButton());
            root.getChildren().add(new KataminoLongButton());
            root.getChildren().add(new KataminoChangeButton());
            KataminoDragCell dragCell = new KataminoDragCell();
            dragCell.customizeCell(-1, false, Color.SALMON);
            root.getChildren().add(dragCell);
            dragCell.customizeCell(-2, false, Color.SALMON);

            Scene scene = new Scene(root,150,38);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
