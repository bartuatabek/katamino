import javafx.scene.paint.Color;
import kataminoDragCell.KataminoDragCell;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class GameBoard {

    private  KataminoDragCell[][] grid;

    private Level currentLevel;
    private Integer[][] solution;


    private ArrayList<Color> colorList = new ArrayList<Color>(){{
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

    public Integer[][] getBoard() {
        return board;
    }

    public void setBoard(Integer[][] board) {
        this.board = board;
    }

    Integer[][]board;

    public GameBoard(int levelNo) {

        this.currentLevel = new Level(levelNo);
        this.board = currentLevel.getBoard();
        this.grid = new KataminoDragCell[board.length][board[0].length];
        if(currentLevel != null){
            solution = currentLevel.getSolution();
        }

        if(solution == null){
            System.out.println("why");
        }
        try {
            loadLevel();
        } catch (FileNotFoundException f) {
            System.out.println("IOException");
        } catch (IOException ex) {
            System.out.println("General exception");
        }
    }


    public GameBoard(Integer[][] board) {
       this.board=board;
        this.grid = new KataminoDragCell[board.length][board[0].length];
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
                } else if (currentCell.getPentominoInstanceID() == -2) {
                    currentCell.setCellColor(Color.gray(0.4));
                }
                grid[i][j] = currentCell;
            }
        }
    }
    public ArrayList<ArrayList<Integer>> findHintCoords(int pentoNo){
        ArrayList<ArrayList<Integer>> hintCoord = new ArrayList<>();
        System.out.println(solution);
        for (int i= 0; i < this.solution.length; i++) {
            for (int j = 0; j < this.solution[0].length; j++) {
                ArrayList<Integer> coord = new ArrayList<>();
                if(this.solution[i][j] == pentoNo){
                    coord.add(i);
                    coord.add(j);
                    hintCoord.add(coord);
                }
            }
        }
        return hintCoord;
    }

    public KataminoDragCell[][] getGrid() {
        return grid;
    }

    public void setGrid(KataminoDragCell[][] grid) {
        this.grid = grid;
    }

    public void updateGameBoard(int levelNo) {
        this.currentLevel = new Level(levelNo);
        this.board = this.currentLevel.getBoard();

        try {
            loadLevel();
        } catch (FileNotFoundException f) {
            System.out.println("IOException");
        } catch (IOException ex) {
            System.out.println("General exception");
        }
    }
}