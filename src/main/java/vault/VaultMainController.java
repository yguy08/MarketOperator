package vault;

import java.util.ArrayList;
import java.util.List;

import asset.Asset;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import market.Market;

public class VaultMainController {
	
	@FXML private Button showNewBtn;
	@FXML private Button showOpen;
	@FXML private Button showClose;
	@FXML private Button backTest;
	
	@FXML private Text dayRange;
	@FXML private Text balance;
	@FXML private Text risk;
	@FXML private Text stop;
	@FXML private Text units;
	
	@FXML private VBox buttonBox;
	
	@FXML private ListView<String> mainListView;
	
	final ObservableList<String> mainListItems = FXCollections.observableArrayList("Welcome!");
	
	@FXML
	protected void showNewEntries(ActionEvent ev){
		Market m = VaultMain.getMarket();
		List<String> myList = new ArrayList<>();
		for(Asset a : m.getAssetList()){
			myList.add(a.getAssetName());
		}
		
		mainListItems.addAll(myList);
	}
	
	@FXML
	protected void showOpenPositions(ActionEvent ev){

	}
	
	@FXML
	protected void showPositionsToClose(ActionEvent ev){

	}
	
	@FXML
	protected void showBacktestResults(ActionEvent ev){

	}
	
	@FXML
	protected void saveSettings(ActionEvent ev){
		
	}
	
	public void initialize(){
		//set initial items in list view..maybe asset names
		mainListView.setItems(mainListItems);
	}

}
