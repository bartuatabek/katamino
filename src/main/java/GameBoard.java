import javafx.scene.paint.Color;
import kataminoDragCell.KataminoDragCell;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class GameBoard {
    private  KataminoDragCell[][] grid;
    private Level currentLevel;
    Integer[][]board;
    private ArrayList<Color> colorList ;

    public GameBoard(int levelNo) {
        currentLevel = new Level(levelNo);
        board = currentLevel.getBoard();
        grid = new KataminoDragCell[board.length][board[0].length];
        colorList = new ArrayList<Color>(){{
            add(Color.web("FF3B30"));
            add(Color.web("FF9500"));
            add(Color.web("FFCC00"));
            add(Color.web("4CD964"));
            add(Color.web("5AFAFA"));
            add(Color.web("007AFF"));
            add(Color.web("5856D6"));
            add(Color.web("FF2D55"));
            add(Color.web("8B572A"));
            add(Color.web("B8E986"));
            add(Color.web("BD10E0"));
            add(Color.web("FF5700"));
        }};
        try {
            loadLevel();
        } catch (FileNotFoundException f) {
            System.out.println("IOException");
        } catch (IOException ex) {
            System.out.println("General exception");
        }
    }

    public void loadLevel() throws IOException {
        for (int i= 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                KataminoDragCell currentCell = new KataminoDragCell();
                Color cellColor = ((board[i][j] % 12) >= 0 && (board[i][j] != 0)) ? colorList.get(board[i][j] % 12) : Color.web("#262626");
                currentCell.customizeCell(board[i][j], board[i][j] == 0, cellColor);
                if (currentCell.getPentominoInstanceID() == 0) {
                    currentCell.setBorderColor(Color.WHITE);
                }
                grid[i][j]=currentCell;
            }
        }
    }

    public KataminoDragCell[][] getGrid() {
        return grid;
    }

    public void setGrid(KataminoDragCell[][] grid) {
        this.grid = grid;
    }
}