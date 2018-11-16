public class GameController {
    private GameModel gameModel;

    public GameController(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public void startGame() {
        gameModel.startStopWatch();
    }
    public void pauseGame() {
        gameModel.pause();
    }
    public void resumeGame() {
        gameModel.resume();
    }
}
