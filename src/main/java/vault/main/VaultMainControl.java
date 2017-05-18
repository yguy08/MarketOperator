package vault.main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import asset.Asset;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import market.Market;
import settings.Settings;
import vault.VaultStart;
import vault.listview.AssetListViewControl;
import vault.listview.EntryListViewControl;
import vault.settings.SettingsControl;

public class VaultMainControl extends BorderPane implements Initializable {
	
	VaultMainControl vaultMainControl;
	
	AssetListViewControl assetListViewControl;
	
	EntryListViewControl entryListViewControl;
	
	SettingsControl settingsControl;
	
	@FXML private Button newEntriesBtn;
	@FXML private Button settingsBtn;
	@FXML private Button saveBtn;
	
	@FXML private Text statusText;
    
	public VaultMainControl() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VaultMainView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        try {
            fxmlLoader.load();            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
	
	@FXML
	public void showNewEntries(){
		setCenter(entryListViewControl);
	}
	
	@FXML
	public void showSettings(){
		setCenter(settingsControl);
		setStatusText("Settings...");
	}

	@FXML
	public void save(){
		if(this.getCenter().getClass().equals(SettingsControl.class)){
			setCenter(assetListViewControl);
			Settings settings = settingsControl.getSettings();
			setStatusText(settings.toString());
		}
	}
	
	public Text getStatusText(){
		return statusText;
	}
	
	public void setStatusText(String status) {
		this.statusText.setText(status);
	}
	
	public void setInitialTableView(){
		Market m = VaultStart.getMarket();
		for(Asset a : m.getAssetList()){
			assetListViewControl.getAssetObservableList().add(a);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		assetListViewControl = new AssetListViewControl();
		entryListViewControl = new EntryListViewControl();
		settingsControl = new SettingsControl();
		setCenter(assetListViewControl);
	}

}
