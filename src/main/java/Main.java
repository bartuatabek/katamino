import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage window;
    private Scene initialScene;

    @Override
    public void start(final Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setTitle("Katamino v2.0");
        Parent root= FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        initialScene = new Scene(root, 750, 450);
        window.setResizable(false);
        window.setScene(initialScene);
        window.show();
    }

    public static void main (String[] args) {
        launch(args);
    }
}