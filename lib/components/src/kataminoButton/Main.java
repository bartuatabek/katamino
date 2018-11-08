package kataminoButton;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            AnchorPane root = new AnchorPane();
            root.getChildren().add(new KataminoButton());
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
