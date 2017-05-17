package vault.listview;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ListViewTest extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		ListViewControl customControl = new ListViewControl();
        stage.setScene(new Scene(customControl));
        stage.show();
	}

}
