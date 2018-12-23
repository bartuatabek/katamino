import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import kataminoBackButton.KataminoBackButton;
import kataminoScoreLabel.KataminoScoreLabel;

import java.io.IOException;
import java.net.URL;
import java.util.*;


public class ScoreBoardController implements Initializable {

    @FXML
    private KataminoBackButton backButton;

    @FXML
    private GridPane gridPane;

    @FXML
    private VBox players;

    @FXML
    private AnchorPane root;

    private FileManager fm;
    private HashMap<String,Integer> playerList;

    private ArrayList<Integer> col2;
    private ArrayList<String> col1;

    public ScoreBoardController() {
        fm = new FileManager();
        playerList = fm.getHighScores();
        HashMap<String,Integer> sortedPlayerList = sortByValue(playerList);
        col2 =new ArrayList<Integer>(sortedPlayerList.values());
        col1 =new ArrayList<String>(sortedPlayerList.keySet());
    }

    private static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm) {
        // Create a list from elements of HashMap
        List<HashMap.Entry<String, Integer> > list =new LinkedList<HashMap.Entry<String, Integer> >(hm.entrySet());

        // Sort the list
        list.sort(new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

    public void initialize(URL location, ResourceBundle resources) {
        int count = 1;
        int reverse = col1.size() - count;
        while (reverse > -1) {
            try {
                KataminoScoreLabel label = new KataminoScoreLabel();
                label.setText(col1.get(reverse) + "       " + col2.get(reverse));
                label.setTextFill(Color.color(Math.random(), Math.random(), Math.random()));
                players.getChildren().add(label);
                reverse--;
                count++;
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    @FXML
    public void backButtonClicked(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        root.getChildren().setAll(pane);
    }
}
