package vault;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import speculator.DigitalSpeculator;

public class VaultSettingsController implements Initializable, ControlledScreen {
	
	ScreensController myController;
	
	@FXML private Button save;
	@FXML private HBox bottomBox;
	@FXML private TextField dateRangeTextField;
	@FXML private TextField balanceTextField;
	@FXML private TextField riskTextField;
	@FXML private TextField unitsTextField;
	@FXML private TextField stopTextField;
	
	private static int dateRange;
	private static BigDecimal balance;
	private static BigDecimal risk;
	private static int units;
	private static BigDecimal stop;
	
	@FXML
	protected void saveSettings(ActionEvent ev){
		setSettings();
		myController.setScreen(ScreensEnum.MAIN.getScreenName());
	}
	
	@Override
    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setTextFields();
		setSettings();
	}
	
	public static int getDateRange(){
		return dateRange; 
	}
	
	private void setDateRange(){
		dateRange = Integer.parseInt(dateRangeTextField.getText().trim());
	}
	
	public static BigDecimal getBalance(){
		return balance;
	}
	
	private void setBalance(){
		balance = new BigDecimal(balanceTextField.getText().trim());
	}
	
	public static BigDecimal getRisk(){
		return risk;
	}
	
	private void setRisk(){
		risk = new BigDecimal(riskTextField.getText().trim());
	}
	
	public static int getUnits(){
		return units;
	}
	
	private void setUnits(){
		units = Integer.parseInt(unitsTextField.getText().trim());
	}
	
	public static BigDecimal getStop(){
		return stop;
	}
	
	private void setStop(){
		stop = new BigDecimal(stopTextField.getText().trim());
	}
	
	private void setTextFields(){
		DigitalSpeculator ds = DigitalSpeculator.createAverageRiskSpeculator();
		dateRangeTextField.setText(Integer.toString(ds.getTimeFrameDays()));
		balanceTextField.setText((ds.getAccountBalance().toString()));
		riskTextField.setText(ds.getRisk().toString());
		unitsTextField.setText(Integer.toString(ds.getMaxUnits()));
		stopTextField.setText(ds.getStopLength().toString());
	}
	
	private void setSettings(){
		setDateRange();
		setBalance();
		setRisk();
		setUnits();
		setStop();
	}
	

}
