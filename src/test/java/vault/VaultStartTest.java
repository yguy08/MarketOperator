package vault;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import vault.main.VaultMainControl;

public class VaultStartTest extends Application {
	
	Image icon = new Image(getClass().getResourceAsStream("icons/icon-treesun-64x64.png"));

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		VaultMainControl customControl = new VaultMainControl();
        stage.setScene(new Scene(customControl));
        stage.setTitle("Speculation 1000");
        stage.getIcons().add(icon);
        stage.show();
	}

}
