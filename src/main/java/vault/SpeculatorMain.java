package vault;

import com.sun.javafx.application.LauncherImpl;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import vault.VaultPreloader.MarketConsumer;

public class SpeculatorMain extends Application implements MarketConsumer {
	String marketName = null;
    Label l = new Label("");
    Stage stage = null;
    String data = null;
    
    private void mayBeShow() {
        // Show the application if it has credentials and 
        // the application stage is ready
        if (marketName != null && stage != null && data != null) {
            Platform.runLater(new Runnable() {
                public void run() {
                    stage.show();
                }                
            });
        }
    }
    
	public static void main(String[] args) {
	    LauncherImpl.launchApplication(SpeculatorMain.class, VaultPreloader.class, args);
	}
    
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setScene(new Scene(l, 400, 400));
        mayBeShow();
    }
 
    public void setMarket(String marketName) {
        this.marketName = marketName;
        l.setText("Hello "+marketName+"!");
        mayBeShow();
    }
}
