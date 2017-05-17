package vault.preloader;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;

public class PreloaderControl extends BorderPane {
	
	@FXML private ProgressBar bar;
	
	public PreloaderControl() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PreloaderView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        try {
            fxmlLoader.load();            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
	
	public ProgressBar getProgressBar() {
		return bar;
	}

}
