public class GameBoardController {
    private GameBoardModel gameBoardModel;

    public GameBoardController(Level level) {
        this.gameBoardModel = new GameBoardModel(level.getBoard());
    }

    // TODO implement is full?
    public boolean isFull() {
        return false;
    }

    public void movePentomino() {}

    // TODO implement clash check
    private boolean clashCheck() {
        int pentominoID = 0;
        if( gameBoardModel.getGrid().get(0).getPentominoInstanceID() == pentominoID)
            return true;
        return false;
    }

    // draw clashing cells and draw border are not put here because they are related to ui





}
