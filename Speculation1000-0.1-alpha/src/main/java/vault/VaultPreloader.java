package vault;

import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import market.Market;

public class VaultPreloader extends Preloader {
    
	public static interface MarketConsumer {
        public void setMarket(String marketName);
    }
    
    Stage stage = null;
    MarketConsumer consumer = null;
    String marketName = null;
    
    private Scene createChooseMarketScene() {
        VBox vbox = new VBox();
 
        final Button digitalBtn = new Button("Digital");
        digitalBtn.setPrefSize(200, 50);
        vbox.getChildren().add(digitalBtn);
        
        final Button offlineBtn = new Button("Digital Offline");
        offlineBtn.setPrefSize(200, 50);
        vbox.getChildren().add(offlineBtn);
        

        vbox.setPadding(new Insets(10,10,10,10));
        vbox.setSpacing(10);
        
        digitalBtn.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t) {
                // Save market
                marketName = Market.DIGITAL_MARKET;                 
                // Hide if app is ready
                mayBeHide();
            }
        });
        
        offlineBtn.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t) {
                // Save market
                marketName = Market.POLONIEX_OFFLINE;                 
                // Hide if app is ready
                mayBeHide();
            }
        });
        
        Scene sc = new Scene(vbox, 200, 100);
        return sc;
    }
    
    private Scene createSplashScreen(){
    	Image splashScreen = new Image("splashScreen.png");
    	ImageView iv = new ImageView();
    	iv.setImage(splashScreen);
    	StackPane stackPane = new StackPane();
    	stackPane.getChildren().add(iv);
    	Scene sc = new Scene(stackPane, 200, 100);
		return sc;
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setScene(createChooseMarketScene());
        stage.show();
    }
    
    @Override
    public void handleProgressNotification(ProgressNotification pn) {
        
    }
 
    private void mayBeHide() {
        if (stage.isShowing() && marketName != null && consumer != null) {
            consumer.setMarket(marketName);
            Platform.runLater(new Runnable() {
                public void run() {
                   //stage.hide();
                    stage.setScene(createSplashScreen());

                }
            });
        }
    }
    
    @Override
    public void handleStateChangeNotification(StateChangeNotification evt) {
        if (evt.getType() == StateChangeNotification.Type.BEFORE_START) {
            consumer = (MarketConsumer) evt.getApplication();
            //hide if market selected are entered
            mayBeHide();
        }
    }    
	
	

}
