import javafx.scene.paint.Color;
import kataminoDragCell.KataminoDragCell;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Game {
    GameBoard gameBoard;
    Stopwatch stopwatch;
    boolean stopped;

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
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
        if (!stopped) {
            stopwatch.pause();
            stopped = true;
        }
    }

    public void resume() {
        if (stopped) {
            stopwatch.resume();
            stopped = false;
        }
    }

    public int getElapsedSeconds() {
        return stopwatch.getElapsedTime();
    }



}
