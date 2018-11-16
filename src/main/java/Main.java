import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage window;
    private Scene initialScene, levelMenuScene,hghScrScene;

    @Override
    public void start(final Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setTitle("Hello World");
        Parent root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        initialScene = new Scene(root, 1200, 700);
        window.setScene(initialScene);
        window.show();
    }

    public static void main (String[] args) {
        launch(args);
    }
}