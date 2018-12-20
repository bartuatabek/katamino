import kataminoDragCell.KataminoDragCell;

import java.io.IOException;

public class SinglePlayerGame extends Game {
    private Player player;
    private int gameScore;
    private int levelScore;

    public SinglePlayerGame(int level, int gameScore, Player player) {
        gameBoard = new GameBoard(level);
        this.player = player;
        stopwatch= new Stopwatch();
        this.gameScore = gameScore;
        stopped=false;
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
    }

    public boolean savePlayerBoard() throws IOException {

        KataminoDragCell[][] temp =gameBoard.getGrid();
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

    public void updateLevel(int newLevelNo) {
        gameBoard= new GameBoard(newLevelNo);
        levelScore = 0;
    }

}
