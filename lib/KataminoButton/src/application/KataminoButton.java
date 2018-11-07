package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class KataminoButton extends AnchorPane {

	@FXML
	private ImageView buttonImage;
	
	@FXML
	private Label buttonLabel;
	
	@FXML
	private AnchorPane buttonContainer;
	
	public KataminoButton() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("KataminoButton.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		loader.load();
	}
	
	public void setButtonName(String buttonName) {
		this.buttonLabel.setText(buttonName);
	}
}
