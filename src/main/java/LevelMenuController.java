/**
 * LevelMenu is an interface to choose a level to play.
 * Created by Sena on 13.11.2018.
 */


import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import kataminoBackButton.KataminoBackButton;
import kataminoLevelButton.KataminoLevelButton;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LevelMenuController implements Initializable {
    @FXML
    private KataminoBackButton backButton;

    @FXML
    private GridPane gridPane;

    @FXML
    private AnchorPane root;

    private Player player;      // Added player is already needed inorder to adjust available levels
    // private Level[] LevelList;
    // ObservableList<String> levels = FXCollections.observableArrayList();

    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i < gridPane.getChildren().size();i++) {
            ((KataminoLevelButton)gridPane.getChildren().get(i)).setButtonText("" + (i+1));
        }
    }

    @FXML
    public void backButtonClicked(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("playerSelection.fxml"));
        root.getChildren().setAll(pane);
    }

    @FXML
    public void levelButtonClicked(MouseEvent event) throws IOException {
        FXMLLoader levelMenuLoader = new FXMLLoader(getClass().getResource("singlePlayerGame.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        AnchorPane pane = levelMenuLoader.load();

        SinglePlayerGameController gameController = levelMenuLoader.getController();
        gameController.gameSetup(Integer.parseInt(((KataminoLevelButton)event.getSource()).getButtonText()), player.getHighScore(), player);

        stage.setWidth(1250);
        stage.setHeight(700);

        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        root.getChildren().setAll(pane);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void updateLevelAccess() {
        if (player != null) {
            int accessibleLevelLimit = player.getAccessibleLevel();

            for (int i = 0; i < gridPane.getChildren().size();i++) {
                if (i >= accessibleLevelLimit) {
                    ((KataminoLevelButton)gridPane.getChildren().get(i)).setOpacity(0.5);
                    ((KataminoLevelButton)gridPane.getChildren().get(i)).setDisable(true);
                } else {
                    gridPane.getChildren().get(i).setOnMouseClicked( new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            try {
                                levelButtonClicked(event);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                        }
                    });
                }
            }
        }
    }
}