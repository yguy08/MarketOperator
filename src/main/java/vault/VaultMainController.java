package vault;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import asset.Asset;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
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
	protected void showSettings(ActionEvent ev){
		myController.setScreen(ScreenEnum.SETTINGS.getScreenName());
	}
	
	public void initialize(){
		//set initial items in list view..maybe asset names
		mainListView.setItems(mainListItems);
	}

    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

}
