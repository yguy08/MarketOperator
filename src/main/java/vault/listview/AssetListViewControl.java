package vault.listview;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class AssetListViewControl extends Pane {
	
	public AssetListViewControl() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AssetListView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        try {
            fxmlLoader.load();            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}
