package vault;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

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
import market.Market;
import market.MarketFactory;
import vault.main.VaultMainControl;

public class VaultStart extends Application {
    
    //load market for faster performance inside
    Market market;
    
    VaultMainControl vaultMainControl;
    
    BooleanProperty ready = new SimpleBooleanProperty(false);
    
	public static void main(String[] args) {
	    LauncherImpl.launchApplication(VaultStart.class, VaultPreloaderStart.class, args);
	}
 
    @Override
    public void start(Stage stage) {
    	//load default market (bitcoin) online or offline
    	loadMarket();
        
    	vaultMainControl = new VaultMainControl();
        stage.setScene(new Scene(vaultMainControl));
        stage.setTitle("Speculation 1000");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("icons/icon-treesun-64x64.png")));
        
        // After the app is ready, show the stage
        ready.addListener(new ChangeListener<Boolean>(){
            @Override
			public void changed(
                ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                    if (Boolean.TRUE.equals(t1)) {
                        Platform.runLater(new Runnable() {
                            @Override
							public void run() {
                                stage.show();
                                vaultMainControl.setMarket(market);
                                vaultMainControl.setInitialTableView();
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
                notifyPreloader(new Preloader.ProgressNotification(.3));
            	market = testConnection() ? MarketFactory.createOnlineBitcoinMarket() : MarketFactory.createOfflineBitcoinMarket();
            	notifyPreloader(new Preloader.ProgressNotification(.9));
            	ready.setValue(Boolean.TRUE);
                notifyPreloader(new StateChangeNotification(
                    StateChangeNotification.Type.BEFORE_START));
                return null;
            }
        };
        
        new Thread(task).start();
    }
    
    private boolean testConnection(){
    	try {
            URL myURL = new URL("https://poloniex.com/");
            URLConnection myURLConnection = myURL.openConnection();
            notifyPreloader(new Preloader.ProgressNotification(.5));
            myURLConnection.connect();
            return true;
        } 
        catch (IOException e) {
            return false;
        }
    }

}
