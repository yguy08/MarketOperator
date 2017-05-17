package vault;

import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import vault.preloader.PreloaderControl;

public class VaultPreloaderStart extends Preloader {
    
	Stage stage;
    
    Image icon; 
    
    PreloaderControl customControl;
 
    @Override
	public void start(Stage stage) throws Exception {
        this.stage = stage;
        customControl = new PreloaderControl();
        this.stage.setScene(new Scene(customControl));
        this.stage.setTitle("Speculation 1000");
        icon = new Image(getClass().getResourceAsStream("icons/icon-treesun-64x64.png"));
        this.stage.getIcons().add(icon);
        this.stage.show();
    }
 
    @Override
    public void handleApplicationNotification(PreloaderNotification pn) {
    	if (pn instanceof ProgressNotification) {
            System.out.println("Handle ApplicationNotification: Progress =  " + ((ProgressNotification) pn).getProgress());
            customControl.getProgressBar().setProgress(((ProgressNotification) pn).getProgress());
         } else if (pn instanceof StateChangeNotification) {
        	 System.out.println("Handle ApplicationNotification: State Change = " + ((StateChangeNotification) pn).getType());
             stage.hide();
         }
    }
	
	@Override
    public void handleProgressNotification(ProgressNotification pn) {
        System.out.println("Handle Progress Notification: Progress = " + pn.getProgress());
        customControl.getProgressBar().setProgress(0.25);
    }
 
    @Override
    public void handleStateChangeNotification(StateChangeNotification evt) {
    	System.out.println("Handle State Change " + evt.getType());
    }   
    
}
