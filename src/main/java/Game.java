import java.util.Timer;

public class Game {
    private GameBoard gameBoard;
    private Stopwatch stopwatch;
    private Player player;
    private int gameScore;

    public Game(Level level, int gameScore, Player player) {

        gameBoard = new GameBoard(level);
        this.player = player;
        stopwatch= new Stopwatch();
        this.gameScore = gameScore;
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
    }

    public void resume() {
        stopwatch.resume();
    }

    public long getElapsedSeconds() {
        return stopwatch.getElapsedTime();
    }

}
