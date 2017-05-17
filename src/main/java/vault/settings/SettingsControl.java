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
import vault.listview.ListViewControl;
import vault.main.VaultMainControl;

public class SettingsControl extends GridPane implements Initializable {
	
	VaultMainControl vaultMainControl;
	
	ListViewControl listViewControl;
	
	@FXML private TextField dateRangeTextField;
	
	@FXML private TextField balanceTextField;
	
	@FXML private TextField riskTextField;
	
	@FXML private TextField unitsTextField;
	
	@FXML private TextField stopTextField;
	
	@FXML private Button save;
	
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		listViewControl = new ListViewControl();
	}
	
	

}
