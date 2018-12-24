import java.io.FileNotFoundException;

public class Level {
    private Integer[][] board;
    private Integer[][] solution;

    public Level(int levelNo) {
        try{
            FileManager fm = new FileManager();
             board = fm.loadLevels(levelNo);
             solution = fm.loadSolution(levelNo);
         }
        catch(Exception e)
        {}
    }
    public Integer[][] getSolution() {
        return solution;
    }

    public Integer[][] getBoard() {
        return board;
    }

    public void setBoard(Integer[][] board) {
        this.board = board;
    }

    public void setSolution(Integer[][] solution) {
        this.solution = solution;
    }
}
