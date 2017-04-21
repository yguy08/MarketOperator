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
import market.MarketFactory;

public class VaultPreloader extends Preloader {
    
	public static interface MarketConsumer {
        public void setMarket(String marketName);
    }
    
    Stage stage = null;
    MarketConsumer consumer = null;
    String marketName = null;
    
    //really only need to load a market
    Market market;
    
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
            	MarketFactory mFactory = new MarketFactory();
            	market = mFactory.createMarket(Market.DIGITAL_MARKET);
                
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
    	Scene sc = new Scene(stackPane, 800, 700);
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
    
    private void setMarket() {
        if (stage.isShowing() && market != null && consumer != null) {
            consumer.setMarket(marketName);
            Platform.runLater(new Runnable() {
                public void run() {
                    stage.setScene(createSplashScreen());
                    
                }
            });
        }
    }
    
    private void prepareMarketData(){
        Platform.runLater(new Runnable() {
            public void run() {
            	
            }
        });
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
