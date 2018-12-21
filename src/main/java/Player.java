import java.io.IOException;

public class Player {
    private int highScore;
    private int accessibleLevel;
    private String playerName;
    private Integer[][] latestBoard;
    private long latestTime;

    public Player(int highScore, int accessibleLevel, String playerName, long latestTime) {
        this.highScore = highScore;
        this.accessibleLevel = accessibleLevel;
        this.playerName = playerName;
        this.latestTime=latestTime;

    }

    public Player( String playerName) {
        this.highScore = 0;
        this.accessibleLevel = 1;
        this.playerName = playerName;
        this.latestBoard = null;
        this.latestTime = 0;
    }

    public long getLatestTime() {
        return latestTime;
    }

    public void setLatestTime(long latestTime) {
        this.latestTime = latestTime;
    }

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
        FileManager fm = new FileManager();

        try {
            fm.updatePlayerInfoInFile(this);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setLatestBoard(Integer[][] latestBoard,long latestTime) throws IOException {
        this.latestBoard=latestBoard;
        this.latestTime=latestTime;
        FileManager fm = new FileManager();
        fm.updatePlayerInfoInFile(this);
}

    public Integer[][] getLatestBoard() {
        return latestBoard;
    }

}
