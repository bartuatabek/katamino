import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import kataminoBackButton.KataminoBackButton;
import kataminoButton.KataminoButton;
import kataminoChangeButton.KataminoChangeButton;
import kataminoLevelButton.KataminoLevelButton;
import kataminoLongButton.KataminoLongButton;
import kataminoPlayerAddButton.KataminoPlayerAddButton;
import kataminoSoundButton.KataminoSoundButton;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            AnchorPane root = new AnchorPane();
            root.getChildren().add(new KataminoButton());
            root.getChildren().add(new KataminoLevelButton());
            root.getChildren().add(new KataminoBackButton());
            root.getChildren().add(new KataminoSoundButton());
            root.getChildren().add(new KataminoPlayerAddButton());
            root.getChildren().add(new KataminoLongButton());
            root.getChildren().add(new KataminoChangeButton());
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
