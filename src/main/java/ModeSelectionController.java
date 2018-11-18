import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import kataminoLongButton.KataminoLongButton;

import java.net.URL;
import java.util.ResourceBundle;

public class ModeSelectionController implements Initializable {
    @FXML KataminoLongButton kataminoArcadeButton;
    @FXML KataminoLongButton kataminoCustomButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        kataminoArcadeButton.setButtonName("Classic(Arcade) Mode");
        kataminoCustomButton.setButtonName("Custom Shapes Mode");
    }
}
