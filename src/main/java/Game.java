import java.util.Timer;

public class Game {
    private GameBoard gameBoard;
    private Timer timer;
    private Player player;
    private int gameScore;

    public Game(Level level, int gameScore, Player player) {
        timer = new Timer();
        gameBoard = new GameBoard(level);
        this.player = player;
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

    public void setGameScore(int gameScore) {
        this.gameScore = gameScore;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }
}
