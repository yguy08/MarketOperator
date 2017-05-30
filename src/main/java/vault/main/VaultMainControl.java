package vault.main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import backtest.BackTest;
import backtest.BackTestFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import market.Market;
import speculator.Speculator;
import speculator.SpeculatorControl;
import util.DateUtils;
import vault.listview.MainListViewControl;

public class VaultMainControl extends BorderPane implements Initializable {
	
	private Market market;
	
	private Speculator speculator;
	
	private MainListViewControl mainListViewControl;
	
	private static VaultMainControl vaultMainControl;
	
	private SpeculatorControl speculatorControl;
	
	@FXML private Button newEntriesBtn;
	
	@FXML private Button settingsBtn;
	
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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mainListViewControl = new MainListViewControl();
		speculatorControl = new SpeculatorControl();
		setCenter(mainListViewControl);
		vaultMainControl = this;
	}
	
	@FXML
	public void showNewEntries(){
		speculator = speculatorControl.getSpeculator();
		BackTestFactory bFactory = new BackTestFactory();
		BackTest backtest = bFactory.runBackTest(market, speculator);
		backtest.protoBackTest();
		for(int i = 0; i < backtest.getEntryList().size(); i++){
			int days = DateUtils.getNumDaysFromDateToToday(backtest.getEntryList().get(i).getDateTime());
			if(days <= 7){
				mainListViewControl.getMainObservableList().add(backtest.getEntryList().get(i).toString());
			}
		}
	}
	
	@FXML
	public void showSettings(){
		setCenter(speculatorControl);
		setStatusText("Settings...");
	}
	
	public void setSettings(){
		setCenter(mainListViewControl);
		speculator = speculatorControl.getSpeculator();
		setStatusText(speculator.toString());
	}
	
	public Text getStatusText(){
		return statusText;
	}
	
	public void setStatusText(String status) {
		statusText.setText(status);
	}
	
	public void setInitialTableView(){
		mainListViewControl.getMainObservableList().add("Welcome!");
	}

	public void setMarket(Market market) {
		this.market = market;
	}
	
	public static VaultMainControl getVaultMainControl(){
		return vaultMainControl;
	}

}
