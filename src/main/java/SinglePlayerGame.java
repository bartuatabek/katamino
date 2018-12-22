import kataminoDragCell.KataminoDragCell;

import java.io.IOException;

public class SinglePlayerGame extends Game {
    private Player player;
    private int gameScore;
    private int levelScore;
    private int currentLevel;

    public SinglePlayerGame(int level, int gameScore, Player player) {
        currentLevel = level;
        gameBoard = new GameBoard(currentLevel);
        this.player = player;
        stopwatch= new Stopwatch();
        this.gameScore = gameScore;
        stopped = false;
    }

    public SinglePlayerGame(int level) {
        currentLevel = level;
        gameBoard = new GameBoard(currentLevel);
        stopwatch= new Stopwatch();
        stopped = false;
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

    public void incrementGameScore(int amount) {
        this.gameScore = gameScore+amount;
        player.setHighScore(gameScore);
    }

    public boolean savePlayerBoard() throws IOException {

        KataminoDragCell[][] temp = gameBoard.getGrid();
        Integer[][] getter= new Integer[temp.length ][temp[0].length];
        for (int i =0; i< temp.length ;i++ )
        {
            for(int j=0; j<temp[0].length;j++)
            {
                getter[i][j]= temp[i][j].getPentominoInstanceID();
            }
        }
        player.setLatestBoard(getter,stopwatch.getElapsedTime());
        return true;
    }

    public void updateLevel() throws IOException {
        if (currentLevel == player.getAccessibleLevel()) {
            player.setAccessibleLevel(player.getAccessibleLevel() + 1);
          //  player.setLatestBoard(gameBoard.getBoard(),getElapsedSeconds());
        }

        gameBoard.updateGameBoard(currentLevel + 1);
        levelScore = 0;

    }
    public int getCurrentLevel(){
        return currentLevel;
    }

}
