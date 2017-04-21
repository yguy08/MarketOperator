package vault;

import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import market.Market;

public class VaultPreloader extends Preloader {
	
	//preload a market
	Market market = null;
	
	//first choose market name then splash screen displays while loading market
	String marketName = null;
	
	//choose market buttons
	final Button digitalBtn = new Button("Digital");
	final Button offlineBtn = new Button("Digital Offline");
    
	public static interface MarketConsumer {
        public void setMarket(String marketName);
    }
    
    Stage stage = null;
    MarketConsumer consumer = null;
    
    private Scene createChooseMarketScene() {
        VBox vbox = new VBox();
 
        digitalBtn.setPrefSize(200, 50);
        digitalBtn.setOnAction(e-> ButtonClicked(e));
        vbox.getChildren().add(digitalBtn);
        
        offlineBtn.setPrefSize(200, 50);
        offlineBtn.setOnAction(e-> ButtonClicked(e));
        vbox.getChildren().add(offlineBtn);
        
        vbox.setPadding(new Insets(10,10,10,10));
        vbox.setSpacing(10);
        
        Scene sc = new Scene(vbox, 200, 100);
        return sc;
    }
    
    private void ButtonClicked(ActionEvent e){
        if (e.getSource() == digitalBtn){
        	marketName = Market.DIGITAL_MARKET;
            mayBeHide();
        }else if (e.getSource() == offlineBtn){
        	marketName = Market.POLONIEX_OFFLINE;
            mayBeHide();
        }else{
        	marketName = Market.POLONIEX_OFFLINE;
            mayBeHide();
        }
        
    }
    
    private Scene createSplashScreen(){
    	Image splashScreen = new Image("splashScreen.png");
    	ImageView iv = new ImageView();
    	iv.setImage(splashScreen);
    	StackPane stackPane = new StackPane();
    	stackPane.getChildren().add(iv);
    	Scene sc = new Scene(stackPane, 700, 700);
		return sc;
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(createChooseMarketScene());
        stage.centerOnScreen();
        stage.show();
    }
    
    @Override
    public void handleProgressNotification(ProgressNotification pn) {
        
    }
 
    private void mayBeHide() {
        if (stage.isShowing() && marketName != null && consumer != null) {
        	Platform.runLater(new Runnable() {
                public void run() {
                	stage.setScene(createSplashScreen());
                	stage.show();
                    setMarket();
                }
            });
        }
    }
    
    private void setMarket() {
    	if (stage.isShowing() && marketName != null && consumer != null) {
            Platform.runLater(new Runnable() {
                public void run() {
                	stage.setScene(createSplashScreen());
            		consumer.setMarket(marketName);
            		stage.hide();
                }
            });
        }
    }
    
    @Override
    public void handleStateChangeNotification(StateChangeNotification evt) {
        if (evt.getType() == StateChangeNotification.Type.BEFORE_START) {
            consumer = (MarketConsumer) evt.getApplication();            
            //hide if market selected is entered
            mayBeHide();
        }
    }    
	
	

}
