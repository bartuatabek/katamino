public class Level {
    private Integer[][] board;

    public Integer[][] getBoard() {
        return board;
    }

    public void setBoard(Integer[][] grid) {
        this.board = board;
    }

    public Level(Integer[][] grid)
    {
        this.board = grid;
    }
}
