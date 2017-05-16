package vault;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import entry.Entry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import market.Market;
import speculator.Speculator;
import speculator.SpeculatorFactory;
import trade.Trade;
import trade.TradeFactory;

public class VaultMainController implements Initializable, ControlledScreen {
	
	ScreensController myController;
	
	@FXML private BorderPane borderPane;
	
	@FXML private Button showNewBtn;
	@FXML private Button showOpen;
	@FXML private Button showClose;
	@FXML private Button backTest;
	@FXML private Button settings;
	
	@FXML private Text dayRange;
	@FXML private Text balance;
	@FXML private Text risk;
	@FXML private Text stop;
	@FXML private Text units;
	@FXML private Text statusMain;
	
	@FXML private VBox buttonBox;
	
	@FXML private HBox settingsBox;
	
	@FXML private ListView<String> mainListView;
	
	ObservableList<String> mainListItems = FXCollections.observableArrayList();
	
	@FXML private ToggleButton bitcoinMarket;
	@FXML private ToggleButton dollarMarket;
	@FXML private ToggleButton ethereumMarket;
	
	@FXML
	protected void showNewEntries(ActionEvent ev){
		mainListItems.removeAll(mainListItems);
		Market m = VaultMain.getMarket();
		SpeculatorFactory sFactory = new SpeculatorFactory();
		Speculator s = sFactory.startSpeculating(m);
		TradeFactory tFactory = new TradeFactory();
		Trade trade = tFactory.startTrading();
		trade.setNewEntries(m, s);
		List<Entry> eList = trade.getNewEntries();
		for(Entry e : eList){
			mainListItems.add(e.toString());
		}
	}
	
	@FXML
	protected void showOpenPositions(ActionEvent ev){
		Market m = VaultMain.getMarket();
	}
	
	@FXML
	protected void showPositionsToClose(ActionEvent ev){
		borderPane.setCenter(null);
	}
	
	@FXML
	protected void showBacktestResults(ActionEvent ev){

	}
	
	@FXML
	protected void showSettings(ActionEvent ev){
		myController.setScreen(ScreensEnum.SETTINGS.getScreenName());
	}
	
	public void keyListener(KeyEvent event){		
	    if(event.getCode() == KeyCode.DELETE) {
	    	mainListItems.removeAll(mainListItems);
	     }
	}

	@Override
    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mainListView.setItems(mainListItems);
		bitcoinMarket.setSelected(true);
		dollarMarket.setSelected(false);
		ethereumMarket.setSelected(false);
	}
	
	@FXML
	protected void toggleMarket(ActionEvent ev){
		ToggleButton btnSelected = (ToggleButton) ev.getSource();
		
		String name = btnSelected.getId().toString();
		
		switch(name){
		case "bitcoinMarket":
			bitcoinMarket.setSelected(true);
			dollarMarket.setSelected(false);
			ethereumMarket.setSelected(false);
			break;
		case "dollarMarket":
			dollarMarket.setSelected(true);
			bitcoinMarket.setSelected(false);
			ethereumMarket.setSelected(false);
			break;
		case "ethereumMarket":
			ethereumMarket.setSelected(true);
			dollarMarket.setSelected(false);
			bitcoinMarket.setSelected(false);			
			break;
		default:
			ethereumMarket.setSelected(false);
			dollarMarket.setSelected(false);
			bitcoinMarket.setSelected(true);
		}
	}

}
