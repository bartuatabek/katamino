package kataminoDragCell;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

	@FXML
	private ImageView blockView;

	public KataminoDragCell() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("kataminodragCell.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		loader.load();
	}

	public void customizeCell(int pentominoInstanceID, boolean onBoard, Color cellColor) {
		this.pentominoInstanceID = pentominoInstanceID;
		this.onBoard = onBoard;
        setCellColor(cellColor);

		if (pentominoInstanceID == -1) {
			cell.setPrefWidth(56);
			cell.setPrefHeight(56);
		} else if (pentominoInstanceID == -2) {
			blockView.setImage(new Image(new File("src/resources/kataminoDragCell.png").toURI().toString()));
			blockView.setVisible(true);
			setDisable(true);
		}
	}

	public void setBlocked(Boolean blocked) {
		if (blocked) {
			blockView.setVisible(true);
			setDisable(true);
		} else {
			blockView.setVisible(false);
			setDisable(false);
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
		setColor(cellColor);
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
