import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import kataminoDragBlock.KataminoDragBlock;
import kataminoDragCell.KataminoDragCell;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import kataminoDragBlock.KataminoDragBlock;
import kataminoDragCell.KataminoDragCell;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;


public class MultiplayerGameController extends GameController {
    @FXML
    Label playerLabel2;

    public void loadBoard() throws FileNotFoundException {
        for (int i= 0; i < game.getGameBoard().getGrid().length; i++) {
            for (int j = 0; j < game.getGameBoard().getGrid()[0].length; j++) {
                KataminoDragCell currentCell = (KataminoDragCell) gameTilePane.getChildren().get((i*22)+j);
                KataminoDragCell temp = game.getGameBoard().getGrid()[i][j];
                int cellId = temp.getPentominoInstanceID();
                currentCell.setPentominoInstanceID(cellId);
                if (cellId == 0)
                    currentCell.setBorderColor(Color.WHITE);
                currentCell.setCellColor(temp.getColor());
                currentCell.setOnBoard(temp.isOnBoard());
                currentCell.setOnMousePressed( new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        generatePreview(event);
                    }
                });
            }
        }
        animate(false);
    }

    public void updateStopwatch() {
        Timeline stopwatchChecker = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (!game.isStopped()) {
                stopwatchLabel.setText(String.valueOf(String.format("%02d:%02d", game.getElapsedSeconds() / 60, game.getElapsedSeconds() % 60)));
            }
        }));
        Timeline turnChecker = new Timeline(new KeyFrame(Duration.seconds(10), event -> {
            game.setStopwatch(new Stopwatch());
            game.startStopWatch();

            if (((MultiplayerGame) game).getTurn() == MultiplayerGame.Turn.Player1) {
                ((MultiplayerGame) game).setTurn(MultiplayerGame.Turn.Player2);
                playerLabel.setTextFill(Color.web("#ffe500"));
                playerLabel2.setTextFill(Color.web("#808080"));
            } else {
                ((MultiplayerGame) game).setTurn(MultiplayerGame.Turn.Player1);
                playerLabel2.setTextFill(Color.web("#ffe500"));
                playerLabel.setTextFill(Color.web("#808080"));
            }
        }));
        stopwatchChecker.setCycleCount(Timeline.INDEFINITE);
        stopwatchChecker.play();
        turnChecker.setCycleCount(Timeline.INDEFINITE);
        turnChecker.play();
    }

    public void startGame() {
        game.startStopWatch();
        updateStopwatch();
    }

    public void pauseGame() {
        stopwatchLabel.setText("▶️" + stopwatchLabel.getText());
        game.pause();
    }
private  ArrayList<Node> helperGroupFinder(KataminoDragCell cell){

        ArrayList<Node> friends = new ArrayList<>();
        int cellID= cell.getPentominoInstanceID();
        friends.add(cell);
        KataminoDragCell currentCell;
        int i = 0;
    ObservableList<Node> cells = gameTilePane.getChildren();
    Integer colIndex;
    Integer rowIndex;
        do {
            currentCell = (KataminoDragCell) friends.get(i);
            i++;
            KataminoDragCell currentPentomino;
            Integer[] location = findLocationTilePane(currentCell, gameTilePane);
            rowIndex = location[0];
            colIndex = location[1];
            if (colIndex + 1 <= 21) {
                currentPentomino = (KataminoDragCell) cells.get(rowIndex * 22 + colIndex + 1);
                if (!(friends.contains(currentPentomino))&&(cellID==currentPentomino.getPentominoInstanceID()) ) {
                    friends.add(currentPentomino);
                }
            }

            if (colIndex - 1 >= 0) {
                currentPentomino = (KataminoDragCell) cells.get(rowIndex * 22 + colIndex - 1);
                if (!(friends.contains(currentPentomino)) &&(cellID==currentPentomino.getPentominoInstanceID())) {
                    friends.add(currentPentomino);
                }
            }
            if (rowIndex + 1 <= 10) {
                currentPentomino = (KataminoDragCell) cells.get((rowIndex + 1) * 22 + colIndex);
                if (!(friends.contains(currentPentomino))&&(cellID==currentPentomino.getPentominoInstanceID()) ) {
                    friends.add(currentPentomino);
                }
            }
            if (rowIndex - 1 >= 0) {
                currentPentomino = (KataminoDragCell) cells.get((rowIndex - 1) * 22 + colIndex);
                if (!(friends.contains(currentPentomino))&&(cellID==currentPentomino.getPentominoInstanceID())) {
                    friends.add(currentPentomino);
                }
            }
        } while (friends.size() > i);
        return friends;
}

    public ArrayList<ArrayList<ArrayList<Integer>>> gettingLenghtValue(ArrayList<Node> currentSearch)
    {
        ArrayList<ArrayList<ArrayList<Integer>>> actreturnList=new ArrayList<>();
        ObservableList<Node> cells = gameTilePane.getChildren();
        int emptySize= currentSearch.size();
        if(emptySize<5)
            return null;
        KataminoDragCell currentCell;
        Integer colIndex;
        int pentominoInstanceID =-1;//empty board cell
        Integer rowIndex;
        while(currentSearch.size()>4) {
            ArrayList<ArrayList<Integer>> returnList= new ArrayList<>();
            ArrayList<Node> friends = new ArrayList<>();
            friends.add(currentSearch.get(0));
            int i = 0;
            // int connection=0;
            do {
                currentCell = (KataminoDragCell) friends.get(i);
                i++;
                KataminoDragCell currentPentomino;
                Integer[] location = findLocationTilePane(currentCell, gameTilePane);
                rowIndex = location[0];
                colIndex = location[1];
                if (colIndex + 1 <= 21) {
                    currentPentomino = (KataminoDragCell) cells.get(rowIndex * 22 + colIndex + 1);
                    if (!(friends.contains(currentPentomino)) && (currentSearch.contains(currentPentomino))) {
                        friends.add(currentPentomino);
                    }
                }

                if (colIndex - 1 >= 0) {
                    currentPentomino = (KataminoDragCell) cells.get(rowIndex * 22 + colIndex - 1);
                    if (!(friends.contains(currentPentomino)) && (currentSearch.contains(currentPentomino))) {
                        friends.add(currentPentomino);
                    }
                }
                if (rowIndex + 1 <= 10) {
                    currentPentomino = (KataminoDragCell) cells.get((rowIndex + 1) * 22 + colIndex);
                    if (!(friends.contains(currentPentomino)) && (currentSearch.contains(currentPentomino))) {
                        friends.add(currentPentomino);
                    }
                }
                if (rowIndex - 1 >= 0) {
                    currentPentomino = (KataminoDragCell) cells.get((rowIndex - 1) * 22 + colIndex);
                    if (!(friends.contains(currentPentomino)) && (currentSearch.contains(currentPentomino))) {
                        friends.add(currentPentomino);
                    }
                }
            } while (friends.size() > i);
            ArrayList<Integer> pair;
            ArrayList<ArrayList<Integer>> corr = new ArrayList<>();

            for (int m = 0; m < friends.size(); m++) {
                currentSearch.remove(friends.get(m));
                pair = new ArrayList<>();
                currentCell = (KataminoDragCell) friends.get(m);
                Integer[] location = findLocationTilePane(currentCell, gameTilePane);
                rowIndex = location[0];
                colIndex = location[1];
                pair.add(rowIndex);
                pair.add(colIndex);
                corr.add(pair);
            }
            if (friends.size() > 4) {
                Collections.sort(corr, new Comparator<List<Integer>>() {
                    public int compare(List<Integer> o1, List<Integer> o2) {
                        return o1.get(0).compareTo(o2.get(0));
                    }
                });

                int earlierRow = corr.get(0).get(0);
                int nowRow = corr.get(0).get(0);
                int startIndex = 0;
                int endIndex = 0;

                ArrayList<ArrayList<ArrayList<Integer>>> rowLength = new ArrayList<>();
                ArrayList<Integer> lenList = new ArrayList<>();
                for (int t = 0; t < corr.size(); t++) {
                    earlierRow = nowRow;
                    nowRow = corr.get(t).get(0);

                    if (nowRow != earlierRow) {
                        endIndex = t;
                        //   List<ArrayList<Integer>>  list= Arrays.asList(corr.subList(startIndex,endIndex));
                        ArrayList<ArrayList<Integer>> arraylist = new ArrayList<>(corr.subList(startIndex, endIndex));
                        rowLength.add(arraylist);
                        startIndex = endIndex;
                    }
                    if (t == (corr.size() - 1)) {
                        endIndex = t + 1;
                        ArrayList<ArrayList<Integer>> arraylist = new ArrayList<>(corr.subList(startIndex, endIndex));
                        rowLength.add(arraylist);
                    }
                }
                System.out.println("Corr:" + corr);
                System.out.println("rOW Lenght :" + rowLength);
                for (int f = 0; f < rowLength.size(); f++) {
                    Collections.sort(rowLength.get(f), new Comparator<List<Integer>>() {
                        public int compare(List<Integer> o1, List<Integer> o2) {
                            return o1.get(1).compareTo(o2.get(1));
                        }
                    });
                    int maxLenght = 1;
                    int currentLenght = 1;
                    int lastIndex = rowLength.get(f).get(0).get(1);
                    for (int k = 1; k < rowLength.get(f).size(); k++) {
                        if (lastIndex + 1 == (rowLength.get(f).get(k).get(1))) {
                            currentLenght++;
                            lastIndex = lastIndex + 1;
                        } else {
                            maxLenght = currentLenght;
                            currentLenght = 1;
                            lastIndex = (rowLength.get(f).get(k).get(1));
                        }
                        if (maxLenght < currentLenght)
                            maxLenght = currentLenght;
                    }
                    lenList.add(maxLenght);
                }
                if(!lenList.isEmpty())
                 returnList.add(lenList);

                System.out.println("rOW Lenght :" + rowLength);
                //same thing for col

                Collections.sort(corr, new Comparator<List<Integer>>() {
                    public int compare(List<Integer> o1, List<Integer> o2) {
                        return o1.get(1).compareTo(o2.get(1));
                    }
                });
                int earlierCol = corr.get(0).get(1);
                int nowCol = corr.get(0).get(1);
                startIndex = 0;
                endIndex = 0;
                ArrayList<ArrayList<ArrayList<Integer>>> colLength = new ArrayList<>();
                ArrayList<Integer> wideList = new ArrayList<>();
                for (int t = 0; t < corr.size(); t++) {
                    earlierCol = nowCol;
                    nowCol = corr.get(t).get(1);

                    if (nowCol != earlierCol) {
                        endIndex = t;
                        ArrayList<ArrayList<Integer>> arraylist = new ArrayList<>(corr.subList(startIndex, endIndex));
                        colLength.add(arraylist);
                        startIndex = endIndex;
                    }
                    if (t == (corr.size() - 1)) {
                        endIndex = t + 1;
                        ArrayList<ArrayList<Integer>> arraylist = new ArrayList<>(corr.subList(startIndex, endIndex));
                        colLength.add(arraylist);
                    }
                }
                System.out.println("col Lenght :" + colLength);


                for (int f = 0; f < colLength.size(); f++) {
                    Collections.sort(colLength.get(f), new Comparator<List<Integer>>() {
                        public int compare(List<Integer> o1, List<Integer> o2) {
                            return o1.get(0).compareTo(o2.get(0));
                        }
                    });
                    System.out.println("col Lenght.get(f) :" + colLength.get(f));
                    int maxLenght = 1;
                    int currentLenght = 1;
                    int lastIndex = colLength.get(f).get(0).get(0);
                    for (int k = 1; k < colLength.get(f).size(); k++) {
                        if (lastIndex + 1 == (colLength.get(f).get(k).get(0))) {
                            currentLenght++;
                            lastIndex = lastIndex + 1;
                        } else {
                            maxLenght = currentLenght;
                            currentLenght = 1;
                            lastIndex = (colLength.get(f).get(k).get(0));
                        }
                        if (maxLenght < currentLenght)
                            maxLenght = currentLenght;
                    }
                    wideList.add(maxLenght);
                }
                if(!wideList.isEmpty())
                    returnList.add(wideList);

            }
            if(!returnList.isEmpty())
                 actreturnList.add(returnList);
        }
        return actreturnList;
    }

    public boolean isLeftPossibleMove(){
        ObservableList<Node> cells = gameTilePane.getChildren();
        ArrayList<Node> currentSearch = new ArrayList<>();
        ArrayList<Node> currentOutside = new ArrayList<>();

        for (int i = 0; i < cells.size(); i++) {
                KataminoDragCell currentCell = (KataminoDragCell) cells.get(i);
                if ((currentCell.isOnBoard() && (currentCell.getPentominoInstanceID() == -1)) || (currentCell.getPentominoInstanceID() == 0)) {
                    currentSearch.add(currentCell);
                }
                if ((!(currentCell.isOnBoard())) && (currentCell.getPentominoInstanceID() > 0) && (!(currentOutside.contains(currentCell)))) {
                    currentOutside.add(currentCell);
                }
                if (currentCell.isOnBoard() && (currentCell.getPentominoInstanceID() > 0)) {
                   ArrayList<Node> list= helperGroupFinder(currentCell);
                    for(Node x:list) {
                        ((KataminoDragCell) x).setPentominoInstanceID(-2);
                        ((KataminoDragCell) x).setCellColor(Color.gray(0.4));
                    }
                }
            }


        ArrayList<ArrayList<ArrayList<Integer>> >empties = gettingLenghtValue(currentSearch);
        ArrayList<ArrayList<ArrayList<Integer>>> pentos = gettingLenghtValue(currentOutside);
        System.out.println("Empties:"+empties.toString());
        System.out.println("Pentos:"+pentos.toString());
        for(int currentEmpty=0;currentEmpty<empties.size();currentEmpty++) {
            System.out.println("Empty id:"+currentEmpty+"\n");
            ArrayList<ArrayList<Integer>> curEmpty= empties.get(currentEmpty);
            System.out.println("pentos size:"+pentos.size());
            for (int currentPento = 0; currentPento < pentos.size(); currentPento++) {
                System.out.println("Pento id:"+currentPento+"\n");
                ArrayList<ArrayList<Integer>> curP= pentos.get(currentPento);
                System.out.println("Pento :"+curP);
                if (curP.get(0).size()>curEmpty.get(0).size()&&curP.get(0).size()>curEmpty.get(1).size())
                   continue;
                if (curP.get(1).size()>curEmpty.get(0).size()&&curP.get(1).size()>curEmpty.get(1).size())
                   continue;
                if (curP.get(0).size()>=curEmpty.get(0).size())
                {
                    System.out.println("Pentomino ilki daha uzun");
                    if(curP.get(1).size()<=curEmpty.get(1).size())
                    {
                    boolean turn=isThere(curEmpty,curP,1,0);
                    System.out.println("İs there result "+turn);
                    if(!turn)
                        continue;
                    else{
                        System.out.println("İkinci elemana bakılıyor ");
                     turn= isThere(curEmpty,curP,0,1);
                        if(turn)
                        {
                            return true;
                        }
                    }
                    }
                }
                if(curP.get(0).size()<=curEmpty.get(0).size())
                {
                    System.out.println("Pentomino ilki daha kısa");
                    if(curP.get(1).size()<=curEmpty.get(1).size()) {
                        boolean turn = isThere(curEmpty, curP, 0, 0);
                        System.out.println("isthere result " + turn);
                        if (!turn)
                            continue;
                        else {
                            System.out.println("İkinci elemana bakılıyor ");
                            turn = isThere(curEmpty, curP, 1, 1);
                            System.out.println("ikincieleman is there " + turn);
                            if (turn) {
                                return true;
                            }
                        }
                    }
                    if (curP.get(1).size()>=curEmpty.get(1).size()) {
                        boolean turn=isThere(curEmpty,curP,1,0);
                        System.out.println("İs there result "+turn);
                        if(!turn)
                            continue;
                        else{
                            System.out.println("İkinci elemana bakılıyor ");
                            turn= isThere(curEmpty,curP,0,1);
                            if(turn)
                            {
                                return true;
                            }
                        }
                    }
                }
                System.out.println("BURAYA GELDİİİİİ");
            }

        }
        return false;
    }
    private boolean isThere(ArrayList<ArrayList<Integer>> curEmpty,ArrayList<ArrayList<Integer>> curP,int first,int second){
     boolean  turn=false;
        for(int loop=0;loop<2;loop++) {
            int searchIndex = 0;
            for (int i = 0; i < curEmpty.get(first).size(); i++) {
                boolean half = true;
                if ((curP.get(second).get(searchIndex) <= curEmpty.get(first).get(i))) {
                    searchIndex++;
                    half=false;
                    if(searchIndex<curP.get(second).size())
                        for (int y = 1; y < curP.get(second).size() ; y++) {
                            if(curEmpty.get(first).size()>i+y)
                                if (curP.get(second).get(searchIndex) <= curEmpty.get(first).get(i + y))
                                {    System.out.println(curP.get(second).get(searchIndex)+"   "+ curEmpty.get(first).get(i + y) );
                                    searchIndex++;
                                    half=true;
                                    System.out.println("search index: "+searchIndex);}
                                else {
                                    half = false;
                                    break;
                                }
                        }
                    if (half) {
                        System.out.println("Half doğru dememkki içinde var");
                        System.out.println("half doğru olunca searchIndex:"+searchIndex+"ve curp size:"+  curP.get(second).size());
                        if (searchIndex >= curP.get(second).size()) {
                            turn = true;
                            System.out.println("Break cause wanted found");
                            break;
                        }
                    }
                    else {
                        searchIndex = 0;
                    }
                }
            }
            if (!turn) {
                System.out.println("Pentomino ters hali deneniyo");
                Collections.reverse(curP.get(second));
            }
            else
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        game = new MultiplayerGame(2); /////////////////////////////////BOARDCHOOSE
        try {
            kataminoDragCell = new KataminoDragCell();
            loadBoard();
            preview = new KataminoDragBlock();
            gridStack.setOnKeyPressed(keyPressed);
            gridStack.getChildren().add(preview);
            gridStack.setVisible(false);
        } catch (Exception e) {
            System.out.println(e);
        }
        playerLabel.setText("Player 1");
        playerLabel2.setText("Player 2");
        startGame();
        gameTilePane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) { preview.fireEvent(event);
            }
        });
        gameTilePane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                preview.fireEvent(event);
            }
        });
        EventHandler eventHandler = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if (gridStack.isVisible()) {
                    KataminoDragCell[][] temp= new KataminoDragCell[11][22];
                    try {
                        int rowNode = 0;
                        int colNode = 0;
                        for (Node node : gameTilePane.getChildren()) {
                            if (node instanceof KataminoDragCell) {
                                if (node.getBoundsInParent().contains(event.getSceneX(), event.getSceneY())) {
                                    Integer[] location = findLocationTilePane(node, gameTilePane);
                                    if (location[0] != null){
                                        rowNode = location[0] - 1;
                                    }
                                    if (location[1] != null)
                                    {
                                        colNode = location[1];
                                    }
                                }
                            }
                        }
                        for(int o =0;o<11;o++)
                        {
                            for(int k =0;k<22;k++){
                                temp[o][k]=(KataminoDragCell) gameTilePane.getChildren().get(o*22+k);
                            }
                        }
                        game.getGameBoard().setGrid(temp);
                        gridStack.setVisible(clashCheck(rowNode, colNode));
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    ((MultiplayerGame)game).changeTurn();
                    System.out.println(((MultiplayerGame)game).getTurn().toString());
                    boolean entered=false;
                    if (isFull()||(!(isLeftPossibleMove()))) {
                        entered=true;
                        pauseGame();
                        String winner= ((MultiplayerGame)game).getTurn().toString();
                        stopwatchLabel.setText(winner+" Won!!");
                    }
                    if(!entered) {
                        if (((MultiplayerGame) game).getTurn() == MultiplayerGame.Turn.Player1) {
                            playerLabel2.setTextFill(Color.web("#ffe500"));
                            playerLabel.setTextFill(Color.web("#808080"));
                        } else {
                            playerLabel.setTextFill(Color.web("#ffe500"));
                            playerLabel2.setTextFill(Color.web("#808080"));
                        }
                        game.setStopwatch(new Stopwatch());
                        game.startStopWatch();
                    }
                    }
                }
             };
        gameTilePane.setOnMouseReleased(eventHandler);
        gridStack.setOnMouseReleased(eventHandler);
    }
}


