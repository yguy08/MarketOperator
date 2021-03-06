package vault;

import com.sun.javafx.application.LauncherImpl;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.application.Preloader.StateChangeNotification;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import vault.preloader.VaultPreloaderStart;

@SuppressWarnings("restriction")
public class VaultStart extends Application {
    
    BooleanProperty ready = new SimpleBooleanProperty(false);
    
    static boolean isConnected;
    
	public static void main(String[] args) {
	    LauncherImpl.launchApplication(VaultStart.class, VaultPreloaderStart.class, args);
	}
 
    @Override
    public void start(Stage stage) {
    	notifyPreloader(new Preloader.ProgressNotification(.3));
    	
    	//load default market (bitcoin) online or offline
    	loadMarket();
        
        // After the app is ready, show the stage
        ready.addListener(new ChangeListener<Boolean>(){
            @Override
			public void changed(
                ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                    if (Boolean.TRUE.equals(t1)) {
                        Platform.runLater(new Runnable() {
                            @Override
							public void run() {                              
                                VaultMainControl vaultMainControl = new VaultMainControl();
                                stage.setScene(new Scene(vaultMainControl));
                                stage.setTitle("Speculation 1000");
                                stage.getIcons().add(new Image(getClass().getResourceAsStream("icons/icon-treesun-64x64.png")));
                                stage.show();				
                            }
                        });
                    }
                }
        });;                
    }
    
    private void loadMarket() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
            	Config.ConfigSetUp();
            	ready.setValue(Boolean.TRUE);
                notifyPreloader(new StateChangeNotification(
                    StateChangeNotification.Type.BEFORE_START));
                return null;
            }
        };        
        new Thread(task).start();
    }

}
