package kataminoDragCell;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class KataminoDragCell extends Pane {

	private int pentominoInstanceID;
	private boolean onBoard;
	private Color cellColor;

	@FXML
	private Pane borderPane;
	
	@FXML
	private Pane cell;

	public KataminoDragCell() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("kataminodragCell.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		loader.load();
	}

	public void customizeCell(int pentominoInstanceID, boolean onBoard, Color cellColor) {
		this.pentominoInstanceID = pentominoInstanceID;
		this.onBoard = onBoard;
		this.cellColor = cellColor;

		if (!onBoard) {
			cell.setPrefWidth(53);
			cell.setPrefHeight(56);
		} else {
			borderPane.setDisable(true);
		}
	}

	public Color getColor() {
		return cellColor;
	}

	public void setColor(Color color) {
		this.cellColor = color;
	}

	public void setBorderColor(Color borderColor) {
		borderPane.setStyle("-fx-background-color: #" + Integer.toHexString(borderColor.hashCode()));
	}
	
	public void setCellColor(Color cellColor) {
		this.cellColor = cellColor;
		cell.setStyle("-fx-background-color: #" + Integer.toHexString(cellColor.hashCode()));
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
}
