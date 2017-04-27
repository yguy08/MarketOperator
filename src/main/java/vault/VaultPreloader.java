package vault;

import javafx.application.Preloader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import vault.VaultMain.MarketConsumer;

public class VaultPreloader extends Preloader implements MarketConsumer {
	
	ProgressBar bar;
    
	Stage stage;
    
    boolean noLoadingProgress = true;
    
    //Application Icon
    Image icon = new Image(getClass().getResourceAsStream("resources/icon-treesun-64x64.png"));
 
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("resources/VaultLoaderFXML.fxml"));
        Scene scene = new Scene(root, 570, 320);
        stage.setScene(scene);
        stage.setTitle("Speculation 1000");
        stage.getIcons().add(icon);
        stage.show();
    }
 
    @Override
    public void handleApplicationNotification(PreloaderNotification pn) {
        if (pn instanceof ProgressNotification) {
           //expect application to send us progress notifications 
           //with progress ranging from 0 to 1.0
           double v = ((ProgressNotification) pn).getProgress();
           if (!noLoadingProgress) {
               //if we were receiving loading progress notifications 
               //then progress is already at 50%. 
               //Rescale application progress to start from 50%               
               v = 0.5 + v/2;
           }
           //bar.setProgress(v);  
        } else if (pn instanceof StateChangeNotification) {
            //hide after get any state update from application
            stage.hide();
        }
    }

	@Override
	public void setStatus(String status) {

	}
	
	@Override
    public void handleProgressNotification(ProgressNotification pn) {
        //handle progress
    }
 
    @Override
    public void handleStateChangeNotification(StateChangeNotification evt) {
        //ignore, hide after application signals it is ready
    }

    
    
}
