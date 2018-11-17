import javafx.scene.paint.Color;

import java.util.ArrayList;

public class GameBoard {
    private  Cell[][] grid;

    public GameBoard(Level level) {
        ArrayList<Color> colorList = new ArrayList<>();
        colorList.add(Color.ANTIQUEWHITE);
        colorList.add(Color.GRAY);
        colorList.add(Color.ALICEBLUE);
        colorList.add(Color.AZURE);
        colorList.add(Color.RED);
        colorList.add(Color.CHARTREUSE);
        colorList.add(Color.CORNFLOWERBLUE);
        colorList.add(Color.MEDIUMPURPLE);
        colorList.add(Color.LIME);
        Integer[][] cellNos = level.getGrid();
        grid = new Cell[cellNos.length][cellNos[0].length];//düzenle
        for (int i= 0; i < cellNos.length; i++) {
            for (int j = 0; i < cellNos[i].length; i++) {
                grid[i][j] = new Cell(cellNos[i][j], cellNos[i][j] != 0, colorList.get(cellNos[i][j] % 9));
            }
        }
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public void setGrid(Cell[][] grid) {
        this.grid = grid;
    }
}