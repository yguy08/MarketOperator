package vault.settings;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VaultSettingsTest extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		SettingsControl customControl = new SettingsControl();
        
        stage.setScene(new Scene(customControl));
        stage.show();
		
	}

}
