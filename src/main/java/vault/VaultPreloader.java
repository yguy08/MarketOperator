package vault;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class VaultPreloader extends Preloader {
	
	FXMLLoader fxmlLoader;
    
	Stage stage;
    
    //Application Icon
    Image icon = new Image(getClass().getResourceAsStream("images/icon-treesun-64x64.png"));
 
    @Override
	public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        fxmlLoader = new FXMLLoader(getClass().getResource("VaultPreloaderFXML.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 570, 320);
        stage.setScene(scene);
        stage.setTitle("Speculation 1000");
        stage.getIcons().add(icon);
        stage.show();
    }
 
    @Override
    public void handleApplicationNotification(PreloaderNotification pn) {
    	if (pn instanceof ProgressNotification) {
            System.out.println("Handle ApplicationNotification: Progress =  " + ((ProgressNotification) pn).getProgress());
            fxmlLoader.<VaultPreloaderController>getController().getProgressBar().setProgress(((ProgressNotification) pn).getProgress());
         } else if (pn instanceof StateChangeNotification) {
        	 System.out.println("Handle ApplicationNotification: State Change = " + ((StateChangeNotification) pn).getType());
             stage.hide();
         }
    }
	
	@Override
    public void handleProgressNotification(ProgressNotification pn) {
        System.out.println("Handle Progress Notification: Progress = " + pn.getProgress());
        fxmlLoader.<VaultPreloaderController>getController().getProgressBar().setProgress(0.25);
    }
 
    @Override
    public void handleStateChangeNotification(StateChangeNotification evt) {
    	System.out.println("Handle State Change " + evt.getType());
    }   
    
}
