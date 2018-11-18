public class GameController {
    private GameBoard board;
    public GameController(Level level){
        board= new GameBoard(level);
    }
    private boolean isFull(){
            return false;
    }
    private void placePentomino(){}
    private void movePentomino (){}
    private boolean clashCheck(){
        int pentominoID = 0;
        if( board.getGrid()[0][0].getPentominoInstanceID() == pentominoID) //wrong
            return true;
        return false;
    }
    private void drawBorder(){}
    private void colorClashingCells(){}
    private void startGame(){}
    private void pauseGame(){}
}