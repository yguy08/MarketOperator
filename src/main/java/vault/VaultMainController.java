package vault;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class VaultMainController {
	
	@FXML private Button showNewBtn;
	@FXML private Button showOpen;
	@FXML private Button showClose;
	@FXML private Button backTest;
	
	@FXML private TextField dayRange;
	@FXML private TextField balance;
	@FXML private TextField risk;
	@FXML private TextField stop;
	@FXML private TextField units;
	
	@FXML private VBox buttonBox;
	
	@FXML private ListView<String> mainListView;
	
	final ObservableList<String> mainListItems = FXCollections.observableArrayList("Welcome!");
	
	@FXML
	protected void showNewEntries(ActionEvent ev){
		
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
