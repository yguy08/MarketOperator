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
import settings.Settings;

public class SettingsControl extends GridPane implements Initializable {
	
	@FXML private TextField balanceTextField;
	
	@FXML private TextField riskTextField;
	
	@FXML private TextField maxUnitsTextField;
	
	@FXML private TextField stopLengthTextField;
	
	@FXML private TextField timeFrameDaysTextField;
	
	@FXML private Button save;
	
	Settings settings;
	
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
    	settings = new Settings(Integer.parseInt(balanceTextField.getText().trim()),
    			Integer.parseInt(riskTextField.getText().trim()),
    			Integer.parseInt(maxUnitsTextField.getText().trim()), 
    			Integer.parseInt(stopLengthTextField.getText().trim()), 
    			Integer.parseInt(timeFrameDaysTextField.getText().trim()));
    }
    
    //set text field text to current settings
    private void setSettingsInTextFields(){
    	balanceTextField.setText(Integer.toString(settings.getBalance()));
    	riskTextField.setText(Integer.toString(settings.getRisk()));
    	maxUnitsTextField.setText(Integer.toString(settings.getMaxUnits()));
    	stopLengthTextField.setText(Integer.toString(settings.getStopLength()));
    	timeFrameDaysTextField.setText(Integer.toString(settings.getTimeFrameDays()));
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		settings = new Settings();
		setSettingsInTextFields();
	}
	
	

}
