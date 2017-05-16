package vault;

import java.io.IOException;
import java.net.MalformedURLException;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import market.Market;
import market.MarketFactory;

public class VaultMain extends Application {
	
	Stage stage;
    
	BooleanProperty ready = new SimpleBooleanProperty(false);
	
    //Application Icon
    Image icon = new Image(getClass().getResourceAsStream("images/icon-treesun-64x64.png"));
    
    //market
    static Market market = null;
    
    //Market factory
    MarketFactory mFactory = new MarketFactory();
    
    String marketName;
    
    ScreensController mainContainer = new ScreensController();
    
	public static void main(String[] args) {
	    LauncherImpl.launchApplication(VaultMain.class, VaultPreloader.class, args);
	}
 
    @Override
    public void start(final Stage stage) throws Exception {
    	this.stage = stage;
    	
    	loadMarket();
        
        for(ScreensEnum s : ScreensEnum.values()){
        	mainContainer.loadScreen(s.getScreenName(),s.getFxmlPath());
        }
        
        mainContainer.setScreen(ScreensEnum.MAIN.getScreenName());
        
        Parent root = mainContainer;
        Scene scene = new Scene(root, 570, 320);
        stage.setScene(scene);
        stage.setTitle("Speculation 1000");
        stage.getIcons().add(icon);
        
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
                            }
                        });
                    }
                }
        });;                
    }
    
    public boolean testConnection(){
    	try {
            URL myURL = new URL("https://poloniex.com/");
            URLConnection myURLConnection = myURL.openConnection();
            notifyPreloader(new Preloader.ProgressNotification(.5));
            myURLConnection.connect();
            return true;
        } 
        catch (MalformedURLException e) {
            return false;
        } 
        catch (IOException e) {   
        	return false;
        }
    }
    
    public static Market getMarket(){
    	return market;
    }
    
    private void loadMarket() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
            	// Send progress to preloader
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

}
