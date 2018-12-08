import javafx.scene.paint.Color;
import kataminoDragCell.KataminoDragCell;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;

public class Game {
    private GameBoard gameBoard;
    private Stopwatch stopwatch;
    private Player player;
    private int gameScore;
    private int levelScore;
    private boolean stopped;

    //NEW
    private ArrayList<Color> colorList = new ArrayList<Color>(){{
        add(Color.ANTIQUEWHITE);
        add(Color.GRAY);
        add(Color.ALICEBLUE);
        add(Color.AZURE);
        add(Color.SALMON);
        add(Color.CHARTREUSE);
        add(Color.CORNFLOWERBLUE);
        add(Color.MEDIUMPURPLE);
        add(Color.LIME);
        add(Color.TEAL);
        add(Color.OLIVE);
        add(Color.ORANGERED);
    }};

    public Game(int level, int gameScore, Player player) {
        gameBoard = new GameBoard(level);
        this.player = player;
        stopwatch= new Stopwatch();
        this.gameScore = gameScore;
        stopped=false;
    }
    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getGameScore() {
        return gameScore;
    }

    public void incrementGameScore(int amount) {
        this.gameScore = gameScore+amount;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void startStopWatch() {
        stopwatch.start();
    }

    public void pause() {
        stopwatch.pause();
        stopped=true;
    }

    public void resume() {
        if(stopped)
            stopwatch.resume();
        stopped=false;
    }

    public long getElapsedSeconds() {
        return stopwatch.getElapsedTime();
    }
    public boolean savePlayerBoard() throws IOException {

       KataminoDragCell[][] temp =gameBoard.getGrid();
        Integer[][] getter= new Integer[temp.length ][temp[0].length];
       for (int i =0; i< temp.length ;i++ )
       {
           for(int j=0; j<temp[0].length;j++)
           {
               getter[i][j]= temp[i][j].getPentominoInstanceID();
           }
       }
        player.setLatestBoard(getter,stopwatch.getElapsedTime());
       return true;
    }
    public void updateLevel(int newLevelNo)
    {
        gameBoard= new GameBoard(newLevelNo);
        levelScore=0;
    }

}
