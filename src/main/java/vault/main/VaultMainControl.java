package vault.main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import vault.listview.ListViewControl;
import vault.settings.SettingsControl;

public class VaultMainControl extends BorderPane implements Initializable {
	
	VaultMainControl vaultMainControl;
	
	ListViewControl listViewControl;
	
	SettingsControl settingsControl;
	
	@FXML private Button newEntriesBtn;
	@FXML private Button settings;
	
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
		
	}
	
	@FXML
	public void showSettings(){
		setCenter(settingsControl);
		setStatusText("Settings...");
	}
	
	public void setStatusText(String status) {
		this.statusText.setText(status);
	}

	@FXML
	public void save(){
		if(this.getCenter().getClass().equals(SettingsControl.class)){
			setStatusText("New Settings Saved!");
			setCenter(listViewControl);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listViewControl = new ListViewControl();
		settingsControl = new SettingsControl();
		setCenter(listViewControl);
	}

}
