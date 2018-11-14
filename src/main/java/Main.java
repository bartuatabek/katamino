import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        FXMLLoader psloader = new FXMLLoader(getClass().getResource("playerSelection.fxml"));
        Parent playerSelect  = psloader.load();
        playerSelectionController pscontroller = psloader.getController();

        // suppliying List of player names here, in the future it will come from another place
        String[] pslist = new String[]{"sena", "Hüseyin"};

        // Set the playerList & setSpinner values to playerlist
        pscontroller.setPlayerList(pslist);
        pscontroller.setSpinner();

        //primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.setScene(new Scene(playerSelect, 700, 500));
        primaryStage.show();
    }

    public static void main (String[] args) { launch(args);

    }
}
