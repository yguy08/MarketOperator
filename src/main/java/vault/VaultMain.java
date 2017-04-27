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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import market.Market;
import market.MarketFactory;

public class VaultMain extends Application {
    
	public static interface MarketConsumer {
        public void setStatus(String status);
    }
	
	Stage stage;
    
	BooleanProperty ready = new SimpleBooleanProperty(false);
	
	MarketFactory mFactory = new MarketFactory();
	
	Market market;
	
	MarketConsumer consumer = new VaultPreloader();
	
    
    //Application Icon
    Image icon = new Image(getClass().getResourceAsStream("resources/icon-treesun-64x64.png"));
    
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
                    consumer.setStatus("Setting Markets...");
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
    
    /*private void setMarkets() {
        //simulate long init in background
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
            	String marketName = Market.DIGITAL_MARKET;
            	market = mFactory.createMarket(Market.DIGITAL_MARKET);
                
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
    }*/
 
    @Override
    public void start(final Stage stage) throws Exception {
        // Initiate simulated long startup sequence
    	longStart();
        
        Parent root = FXMLLoader.load(getClass().getResource("resources/VaultMainFXML.fxml"));
        Scene scene = new Scene(root, 570, 320);
        stage.setScene(scene);
        stage.setTitle("Speculation 1000");
        stage.getIcons().add(icon);
        
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
