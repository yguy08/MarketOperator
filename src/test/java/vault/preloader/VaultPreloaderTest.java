package vault.preloader;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VaultPreloaderTest extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		PreloaderControl customControl = new PreloaderControl();
		stage.setScene(new Scene(customControl));
		stage.show();
		
	}

}
