public class Player {
    private int highScore;
    private int accessibleLevel;
    private String playerName;

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public int getAccessibleLevel() {
        return accessibleLevel;
    }

    public void setAccessibleLevel(int accessibleLevel) {
        this.accessibleLevel = accessibleLevel;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Player(int highScore, int accessibleLevel, String playerName) {
        this.highScore = highScore;
        this.accessibleLevel = accessibleLevel;
        this.playerName = playerName;
    }
}
