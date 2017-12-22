package vault.preloader;

import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class VaultPreloaderStart extends Preloader {
    
	Stage stage;
 
    @Override
	public void start(Stage stage) throws Exception {
        this.stage = stage;
        PreloaderControl preloaderControl = new PreloaderControl();
        this.stage.setScene(new Scene(preloaderControl));
        this.stage.setTitle("Speculation 1000");
        this.stage.getIcons().add(new Image(getClass().getResourceAsStream("icon-treesun-64x64.png")));
        this.stage.show();
    }
 
    @Override
    public void handleApplicationNotification(PreloaderNotification pn) {
    	if (pn instanceof StateChangeNotification) {
        	 System.out.println("Handle Application Notification: State Change = " + ((StateChangeNotification) pn).getType());
             stage.hide();
         }
    }
	
	@Override
    public void handleProgressNotification(ProgressNotification pn) {
        System.out.println("Handle Progress Notification: Progress = " + pn.getProgress());
    }
 
    @Override
    public void handleStateChangeNotification(StateChangeNotification evt) {
    	System.out.println("Handle State Change " + evt.getType());
    }
    
}
