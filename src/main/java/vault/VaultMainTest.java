package vault;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class VaultMainTest extends Application {
	
	Image icon = new Image(getClass().getResourceAsStream("icon-treesun-64x64.png"));

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

        ScreensController mainContainer = new ScreensController();
        
        for(ScreenEnum s : ScreenEnum.values()){
        	mainContainer.loadScreen(s.getScreenName(),s.getFxmlPath());
        }
        
        Parent root = mainContainer;
        
        mainContainer.setScreen(ScreenEnum.MAIN.getScreenName());        
        Scene scene = new Scene(root, 570, 320);
        stage.setScene(scene);
        stage.setTitle("Speculation 1000");
        stage.getIcons().add(icon);
        stage.show();
	}

}
