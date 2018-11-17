public class Player {
    private int highScore;
    private int accesibleLevel;
    private String playerName;
    private GameModel currentGame;

    public int getHighScore() {
        return highScore;
    }

    public Level[] getAccesibleLevel() {
        return accesibleLevel;
    }

    public GameModel getCurrentGame() {
        return currentGame;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setAccesibleLevel(Level[] accesibleLevel) {
        this.accesibleLevel = accesibleLevel;
    }

    public void setCurrentGame(GameModel currentGame) {
        this.currentGame = currentGame;
    }

    public Player(int highScore,int accesibleLevel, String playerName) {
        this.highScore = highScore;
        this.accesibleLevel = accesibleLevel;
        this.playerName = playerName;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

}
