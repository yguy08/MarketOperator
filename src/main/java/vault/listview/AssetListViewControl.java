package vault.listview;

import java.io.IOException;
import java.util.List;

import asset.Asset;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;

public class AssetListViewControl extends Pane {
	
@FXML private ListView<Asset> assetListView = new ListView<>();
	
	private ObservableList<Asset> assetObservableList = FXCollections.observableArrayList();
	
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

	public List<Asset> getAssetObservableList() {
		return assetObservableList;
	}

}
