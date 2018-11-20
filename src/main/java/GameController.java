public class GameController {
    private GameBoard board;
    public GameController(Level level){
        board= new GameBoard(level);
    }
    public boolean isFull(){
            return false;
    }
    public void placePentomino(){}
    public void movePentomino (){}
    public boolean clashCheck(){
        int pentominoID = 0;
        if( board.getGrid()[0][0].getPentominoInstanceID() == pentominoID) //wrong
            return true;
        return false;
    }
    public void drawBorder(){}
    public void colorClashingCells(){}
    public void startGame(){}
    public void pauseGame(){}

    public int[][] rotateRight(int[][] pentomino) {
        pentomino = transposeMatrix(pentomino);
        return flipHorizontally(pentomino);
    }

    public int[][] rotateLeft(int[][] pentomino) {
        pentomino = flipHorizontally(pentomino); // Why intelliJ gives a warning here?
        return transposeMatrix(pentomino);
    }

    public int[][] flipVertically(int[][] matrix){
        for(int col = 0;col < matrix[0].length; col++){
            for(int row = 0; row < matrix.length/2; row++) {
                int temp = matrix[row][col];
                matrix[row][col] = matrix[matrix.length - row - 1][col];
                matrix[matrix.length - row - 1][col] = temp;
            }
        }
        return matrix;
    }

    private int[][] transposeMatrix(int[][] matrix){
        int[][] temp = new int[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[0].length; j++)
                temp[j][i] = matrix[i][j];
        return temp;
    }
    private int[][] flipHorizontally(int[][] matrix){
        for(int j = 0; j < matrix.length; j++){
            for(int i = 0; i < matrix[j].length / 2; i++) {
                int temp = matrix[j][i];
                matrix[j][i] = matrix[j][matrix[j].length - i - 1];
                matrix[j][matrix[j].length - i - 1] = temp;
            }
        }
        return matrix;
    }
}