package speculator;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import market.MarketsEnum;
import vault.listview.ControlledList;
import vault.main.VaultMainControl;

public class SpeculatorControl extends GridPane implements Initializable, ControlledList {
	
	@FXML private TextField balanceTextField;
	
	@FXML private TextField riskTextField;
	
	@FXML private TextField maxUnitsTextField;
	
	@FXML private TextField stopLengthTextField;
	
	@FXML private TextField timeFrameDaysTextField;
	
	@FXML private TextField entryTextField;
	
	@FXML private TextField exitTextField;
	
	@FXML private CheckBox longOnlyCheckBox;
	
	@FXML private ToggleButton bitcoinMarket;
	
	@FXML private ToggleButton dollarMarket;
	
	@FXML private ToggleButton ethereumMarket;
	
	@FXML private Button save;
	
	@FXML private ToggleGroup marketToggleGroup;
	
	private Speculator speculator;
	
	private String marketName;
	
    public SpeculatorControl() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SpeculatorView.fxml"));
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
		bitcoinMarket.setSelected(true);
		setMarketName(); 
		setDefaultSettings();		
	}
    
    //set text field text to current settings
    private void setDefaultSettings(){
    	
    	//create speculator with some default settings
    	speculator = new DigitalSpeculator();
    	
    	//populate text fields with initial speculator settings
    	balanceTextField.setText(speculator.getAccountBalance().toPlainString());
    	riskTextField.setText(speculator.getRisk().toPlainString());
    	maxUnitsTextField.setText(Integer.toString(speculator.getMaxUnits()));
    	stopLengthTextField.setText(speculator.getStopLength().toPlainString());
    	timeFrameDaysTextField.setText(Integer.toString(speculator.getTimeFrameDays()));
    	entryTextField.setText(Integer.toString(speculator.getEntrySignalDays()));
    	exitTextField.setText(Integer.toString(speculator.getSellSignalDays()));
    	longOnlyCheckBox.setSelected(speculator.isLongOnly());
    	
    }
    
    private Speculator setSpeculator(){
    	speculator = SpeculatorFactory.createSpeculator(marketName, Integer.parseInt(balanceTextField.getText().trim()),
    			Integer.parseInt(riskTextField.getText().trim()),
    			Integer.parseInt(maxUnitsTextField.getText().trim()), 
    			Integer.parseInt(stopLengthTextField.getText().trim()), 
    			Integer.parseInt(timeFrameDaysTextField.getText().trim()),
    			Integer.parseInt(entryTextField.getText().trim()),
    			Integer.parseInt(exitTextField.getText().trim()),
    			longOnlyCheckBox.isSelected());
    	return speculator;
    }
    
    @FXML
	public void saveSettings(){
    	setSpeculator();
    	VaultMainControl.getVaultMainControl().setInitialTableView();
	}

	public Speculator getSpeculator() {
		return setSpeculator();
	}
    
    public String getMarketName() {
		return marketName;
	}

	private void setMarketName() {		
		if(bitcoinMarket.isSelected()){
			marketName = MarketsEnum.BITCOIN.getMarketName();
		}else{
			marketName = MarketsEnum.DOLLAR.getMarketName();
		}
	}

	@Override
	public void clearList() {
		setDefaultSettings();
	}

	@Override
	public void setList(List<?> list) {
		//Not implemented
	}

	@Override
	public void onKeyEnter(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
