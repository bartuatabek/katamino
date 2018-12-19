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
        grid= new KataminoDragCell[board.length][board[0].length];
         colorList = new ArrayList<Color>(){{
            add(Color.ANTIQUEWHITE);
            add(Color.GRAY);
            add(Color.ALICEBLUE);
            add(Color.AZURE);
            add(Color.SALMON);
            add(Color.CHARTREUSE);
            add(Color.CORNFLOWERBLUE);
            add(Color.MEDIUMPURPLE);
            add(Color.LIME);
            add(Color.TEAL);
            add(Color.OLIVE);
            add(Color.ORANGERED);
        }};
        try {
            loadLevel();

        } catch (FileNotFoundException f) {
            System.out.println("IOException");
        } catch (IOException ex) {
            System.out.println("General exception");
        }
    }
    public void loadLevel() throws FileNotFoundException,IOException {


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