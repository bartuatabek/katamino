import java.util.Timer;
import java.util.TimerTask;
public class MultiplayerGame extends Game{

    Turn turn;
    Timer timer;

    public MultiplayerGame(int boardId) {
        gameBoard = new GameBoard(boardId);
        stopwatch= new Stopwatch();
        turn=Turn.Player1;
        stopped=false;
        TimerTask task = new TimerTask() {
            public void run() {
            changeTurn();
            }
        };
        timer = new Timer("Timer");
        long delay = 2000L;
        timer.schedule(task, delay);
    }
   public enum Turn {Player1,Player2}
    public void changeTurn(){
        if(turn.equals(Turn.Player1))
            turn=Turn.Player2;
        else
            turn=Turn.Player1;
    }
    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }


    public Turn getTurn() {
        return turn;
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
    }



}
