package vault;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class VaultSettingsController implements Initializable, ControlledScreen {
	
	ScreensController myController;
	
	@FXML private Button save;
	
	@FXML private Text dateRange;
	@FXML private Text balance;
	@FXML private Text risk;
	@FXML private Text units;
	@FXML private Text stop;
	@FXML private Text entryFLag;

	@FXML private VBox centerBox;
	
	@FXML private HBox bottomBox;
	
	@FXML
	protected void saveSettings(ActionEvent ev){
		myController.setScreen(ScreenEnum.MAIN.getScreenName());
	}

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

}
