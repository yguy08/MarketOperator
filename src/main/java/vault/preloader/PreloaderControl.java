package vault.preloader;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class PreloaderControl extends BorderPane {
	
	@FXML private Text statusText;
	
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
	
	public void setStatus(String text){
		javafx.application.Platform.runLater( () -> statusText.setText(text) );
	}

}
