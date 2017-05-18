package vault.settings;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import speculator.Settings;
import vault.listview.AssetListViewControl;
import vault.main.VaultMainControl;

public class SettingsControl extends GridPane implements Initializable {
	
	VaultMainControl vaultMainControl;
	
	AssetListViewControl listViewControl;
	
	@FXML private TextField dateRangeTextField;
	
	@FXML private TextField balanceTextField;
	
	@FXML private TextField riskTextField;
	
	@FXML private TextField unitsTextField;
	
	@FXML private TextField stopTextField;
	
	@FXML private Button save;
	
	Settings settings;
	
	private int dateRange;
	
    public SettingsControl() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SettingsView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        try {
            fxmlLoader.load();            
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    
    public Settings getSettings(){
    	setSettings();
    	return settings;
    }
    
    private void setSettings(){
    	dateRange = Integer.parseInt(dateRangeTextField.getText().trim());
    	settings = new Settings();
    	settings.setTimeFrameDays(dateRange);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listViewControl = new AssetListViewControl();
		setSettings();
	}
	
	

}
