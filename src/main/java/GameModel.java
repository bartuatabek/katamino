import java.util.ArrayList;


public class GameModel {
    private GameBoardController gameBoardCtrl;
    private Level level;
    private ArrayList<Player> player;
    private int gameScore;
    private Stopwatch stopwatch;

    public GameModel(Level level, ArrayList<Player> player) {
        this.gameBoardCtrl = new GameBoardController(level);
        this.level = level;
        this.player = player;
        stopwatch = new Stopwatch();
        gameScore=0;
    }

    public void startStopWatch() {
        stopwatch.start();
    }

    public void pause() {
        stopwatch.pause();
    }

    public void resume() {
        stopwatch.resume();
    }
    public long getElapsedSeconds() {
        return stopwatch.getElapsedTime();
    }

    public int getGameScore() {
        return gameScore;
    }

    public void setGameScore(int gameScore) {
        this.gameScore = gameScore;
    }

    public ArrayList<Player> getPlayer() {
        return player;
    }

    public void setPlayer(ArrayList<Player> player) {
        this.player = player;
    }

    public GameModel(Level level) {
        this.level = level;
    }

    public void incrementScore(int amount) {
        gameScore += amount;
    }

}