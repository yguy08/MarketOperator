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
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class VaultMain extends Application {
	
	Stage stage;
    BooleanProperty ready = new SimpleBooleanProperty(false);
    
	public static void main(String[] args) {
	    LauncherImpl.launchApplication(VaultMain.class, VaultPreloader.class, args);
	}
    
    private void longStart() {
        
    	//simulate long init in background
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int max = 10;
                for (int i = 1; i <= max; i++) {
                    Thread.sleep(200);
                    
                    // Send progress to preloader
                    notifyPreloader(new Preloader.ProgressNotification(((double) i)/max));
                }
                
                // After init is ready, the app is ready to be shown
                // Do this before hiding the preloader stage to prevent the 
                // app from exiting prematurely
                ready.setValue(Boolean.TRUE);
 
                notifyPreloader(new StateChangeNotification(
                    StateChangeNotification.Type.BEFORE_START));
                
                return null;
            }
        };
        
        new Thread(task).start();
    }
 
    @Override
    public void start(final Stage stage) throws Exception {
        
    	// Initiate simulated long startup sequence
        longStart();
        
        
        stage.setScene(new Scene(new Label("Application started"), 
            400, 400));
        
        // After the app is ready, show the stage
        ready.addListener(new ChangeListener<Boolean>(){
            public void changed(
                ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                    if (Boolean.TRUE.equals(t1)) {
                        Platform.runLater(new Runnable() {
                            public void run() {
                                stage.show();
                            }
                        });
                    }
                }
        });;                
    }

}
