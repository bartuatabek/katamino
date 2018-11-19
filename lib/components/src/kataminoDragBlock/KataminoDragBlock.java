package kataminoDragBlock;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import kataminoDragCell.KataminoDragCell;

import java.io.IOException;
import java.util.ArrayList;

public class KataminoDragBlock extends GridPane {

    private ArrayList<Color> colorList = new ArrayList<Color>(){{
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
    private int[][] grid;

    @FXML
    private GridPane dragBlockGrid;

    public KataminoDragBlock() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("kataminoDragBlock.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        loader.load();
    }

    public void setPentomino(int[][] grid) {
        this.grid = grid;

        for (int i= 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                KataminoDragCell currentCell = (KataminoDragCell) dragBlockGrid.getChildren().get((i*22)+j);
                Color cellColor = ((grid[i][j] % 12) >= 0 && (grid[i][j] != 0)) ? colorList.get(grid[i][j] % 12) : Color.TRANSPARENT;
                currentCell.customizeCell(grid[i][j], grid[i][j] != 0, cellColor);

                if (currentCell.getPentominoInstanceID() == -1) {
                    currentCell.setBorderColor(Color.TRANSPARENT);
                }
            }
        }
    }
}
