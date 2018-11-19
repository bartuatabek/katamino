/**
 * LevelMenu is an interface to choose a level to play.
 * Created by Sena on 13.11.2018.
 */


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import kataminoBackButton.KataminoBackButton;
import kataminoLevelButton.KataminoLevelButton;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LevelMenuController implements Initializable {
    @FXML private KataminoBackButton backButton;
    @FXML private GridPane gridPane;
    private Player player;      // Added player is already needed inorder to adjust available levels
    // private Level[] LevelList;
    // ObservableList<String> levels = FXCollections.observableArrayList();

    public void initialize(URL location, ResourceBundle resources) {
        for(int i = 0; i < gridPane.getChildren().size();i++)
        {
            ((KataminoLevelButton)gridPane.getChildren().get(i)).setButtonText("" + (i+1));
        }
    }

    @FXML
    public void backButtonClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
        Parent pane = loader.load();
        Scene mainMenuScene = new Scene(pane, 800, 600);

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(mainMenuScene);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}