public class MultiplayerGame extends Game{

   public enum Turn {Player1,Player2}
   Turn turn;

    public Turn getTurn() {
        return turn;
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    public MultiplayerGame() {

    }

}
