import javafx.scene.paint.Color;

public class Cell {
    //attributes
    private int cellNumber;
    private boolean onBoard;
    private Color color;
    //constructor
    public Cell(int cellNumber,boolean onBoard,Color color){
        this.cellNumber=cellNumber;
        this.color=color;
        this.onBoard= onBoard;
    }
    //methods
    public int getCellNumber(){
        return cellNumber;
    }
    public void setCellNumber(int cellNumber){
        this.cellNumber=cellNumber;
    }
    public Color getColor()
    {
        return color;
    }
    public void setColor(Color c2){
        this.color=c2;
    }
    public boolean getOnBoard(){
        return onBoard;
    }
    public void setOnBoard(boolean boardOn){
        this.onBoard=boardOn;
    }
}
