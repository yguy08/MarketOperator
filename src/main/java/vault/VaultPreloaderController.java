package vault;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;

public class VaultPreloaderController extends VaultPreloader implements Initializable {
	
	@FXML private ProgressBar bar;
	@FXML private HBox bottomBox;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	public ProgressBar getProgressBar() {
		return bar;
	}
	
}
