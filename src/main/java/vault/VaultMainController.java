package vault;

import java.net.URL;
import java.util.ResourceBundle;

import asset.Asset;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import market.Market;

public class VaultMainController implements Initializable, ControlledScreen {
	
	ScreensController myController;
	
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
	
	final ObservableList<String> mainListItems = FXCollections.observableArrayList();
	
	@FXML private ToggleButton bitcoinMarket;
	@FXML private ToggleButton dollarMarket;
	@FXML private ToggleButton ethereumMarket;
	
	@FXML
	protected void showNewEntries(ActionEvent ev){
		Market m = VaultMain.getMarket();
		for(Asset a : m.getAssetList()){
			mainListItems.add(a.getAssetName());
		}
		
		mainListView.setItems(mainListItems);
	}
	
	@FXML
	protected void showOpenPositions(ActionEvent ev){
		Market m = VaultMain.getMarket();
		for(Asset a : m.getAssetList()){
			String s = a.getPriceList().get(a.getPriceList().size()-1).toString();
			mainListItems.add(s.toString());
		}
	}
	
	@FXML
	protected void showPositionsToClose(ActionEvent ev){

	}
	
	@FXML
	protected void showBacktestResults(ActionEvent ev){

	}
	
	@FXML
	protected void showSettings(ActionEvent ev){
		myController.setScreen(ScreensEnum.SETTINGS.getScreenName());
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
