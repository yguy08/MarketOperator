package vault.preloader;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class PreloaderControl extends BorderPane implements Initializable {
	
	@FXML private Text statusText;
	
	private static PreloaderControl preloaderControl = null;
	
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		preloaderControl = this;		
	}
    
    public static void updateStatus(String status){
    	if(preloaderControl != null){
    		preloaderControl.setStatus(status);
    	}else{
    		System.out.println(status);
    	}
    }

}
