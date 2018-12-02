public class Level {
    private Integer[][] board;

    public Level(int levelNo) {
        try{
            FileManager fm = new FileManager();
             board = fm.loadLevels(levelNo);

         }
        catch(Exception e)
        {}
    }
    public Integer[][] getBoard() {
        return board;
    }

    public void setBoard(Integer[][] board) {
        this.board = board;
    }
}
