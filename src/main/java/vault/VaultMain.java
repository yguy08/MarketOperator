package vault;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.sun.javafx.application.LauncherImpl;

import javafx.application.Application;
import javafx.application.Platform;
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
	
	Stage stage;
    
	BooleanProperty ready = new SimpleBooleanProperty(false);
	
    //Application Icon
    Image icon = new Image(getClass().getResourceAsStream("icon-treesun-64x64.png"));
    
    //market
    static Market market = null;
    
    //Market factory
    MarketFactory mFactory = new MarketFactory();
    
    String marketName;
    
	public static void main(String[] args) {
	    LauncherImpl.launchApplication(VaultMain.class, VaultPreloader.class, args);
	}
    
    private void loadMarket() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
            	
            	String mName = testConnection() ? Market.DIGITAL_MARKET : Market.DIGITAL_OFFLINE;

            	MarketFactory maFactory = new MarketFactory();
            	Market m = mFactory.createMarket(mName);
            	market = m;
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
    	
    	loadMarket();
    	        
        Parent root = FXMLLoader.load(getClass().getResource("VaultMainFXML.fxml"));
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
    	//OR to save resources...if > an hour...check connection, else use offline? so it is faster...
        try {
            URL myURL = new URL("https://poloniex.com/");
            URLConnection myURLConnection = myURL.openConnection();
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

}
