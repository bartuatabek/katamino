import javafx.scene.paint.Color;

public class Cell {
    int pentominoInstanceID;
    boolean onBoard;
    // bence color burada olmamalı, UIla ilgili
    Color color;


    public Cell(int pentominoInstanceID, boolean onBoard, Color color) {
        this.pentominoInstanceID = -1;
        this.onBoard = onBoard;
        this.color = color;
    }


    public int getPentominoInstanceID() {
        return pentominoInstanceID;
    }

    public void setPentominoInstanceID(int pentominoInstanceID) {
        this.pentominoInstanceID = pentominoInstanceID;
    }

    public boolean isOnBoard() {
        return onBoard;
    }

    public void setOnBoard(boolean onBoard) {
        this.onBoard = onBoard;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}