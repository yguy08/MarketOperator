package vault;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import asset.Asset;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import speculator.DigitalSpeculator;
import speculator.Speculator;
import trade.Entry;
import trade.Exit;
import trade.Trade;

public class VaultMainControl extends BorderPane implements Initializable {
	
	private static VaultMainControl vaultMainControl;
	
	@FXML private ListView<Displayable> listViewDisplay;
	
	@FXML private Button newEntriesBtn;
	
	@FXML private Button showCloseBtn;
	
	@FXML private Button settingsBtn;
	
	@FXML private Button clearListBtn;
	
	@FXML private Button backTestBtn;
	
	@FXML private Text statusText;
		
	private ObservableList<Displayable> mainObsList = FXCollections.observableArrayList();

	private ConfigControl configControl;
    
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
	
	/*
	 * Show assets at or above entry flag, select to view close?
	 */
	@FXML public void showNewEntries(){
		Speculator speculator = new DigitalSpeculator();
		List<Asset> assetList = Config.getMarket().getAssetList();
		Task<List<Entry>> task = new Task<List<Entry>>() {
		    @Override protected List<Entry> call() throws Exception {
		    	List<Entry> entryList = new ArrayList<>();
				for(Asset asset : assetList){
					List<Entry> assetEntryList = asset.getEntryList(speculator);
					entryList.addAll(assetEntryList);					
				}
				
				//sort list
				Collections.sort(entryList, new Comparator<Entry>() {
				    @Override
					public int compare(Entry o1, Entry o2) {
				        return o2.getDateTime().compareTo(o1.getDateTime());
				    }
				});				
		        return entryList;
		    }
		};		
		new Thread(task).start();
		
		task.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
			@Override
			public void handle(WorkerStateEvent t){
				Platform.runLater(new Runnable() {
		            @Override
					public void run() {
		            	mainObsList.setAll(task.getValue());
		            	listViewDisplay.scrollTo(0);
		            }
		        });
			}
		});
     }
	
	@FXML public void showNewExits(){
		Speculator speculator = new DigitalSpeculator();
		Task<List<Exit>> task = new Task<List<Exit>>() {
		    @Override protected List<Exit> call() throws Exception {
				List<Exit> exitList = new ArrayList<>();
				for(Asset a : Config.getMarket().getAssetList()){
					exitList.addAll(a.getExitList(speculator));
				}
				
				//sort list
				Collections.sort(exitList, new Comparator<Exit>() {
				    @Override
					public int compare(Exit exit1, Exit exit2) {
				        return exit2.getDateTime().compareTo(exit1.getDateTime());
				    }
				});
		        return exitList;
		    }
		};
		
		new Thread(task).start();
		task.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
			@Override
			public void handle(WorkerStateEvent t){
				Platform.runLater(new Runnable() {
		            @Override
					public void run() {
		            	mainObsList.setAll(task.getValue());
		            	listViewDisplay.scrollTo(0);
		            }
		        });
			}
		});	
	}
	
	@FXML public void backtest(){
		Speculator speculator = new DigitalSpeculator();
		Task<List<Displayable>> task = new Task<List<Displayable>>() {
		    @Override protected List<Displayable> call() throws Exception {
				List<Exit> exitList = new ArrayList<>();
				for(Asset a : Config.getMarket().getAssetList()){
					exitList.addAll(a.getEntryStatusList(speculator));
				}
				
				//sort list
				Collections.sort(exitList, new Comparator<Exit>() {
				    @Override
					public int compare(Exit exit1, Exit exit2) {
				        return exit1.getEntryDate().compareTo(exit2.getEntryDate());
				    }
				});
				
				Trade t = new Trade(exitList, speculator);
				List<Displayable> resultsList = t.runBackTest();
				
		       return resultsList;		      
		    }
		};
		
		new Thread(task).start();
		task.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
			@Override
			public void handle(WorkerStateEvent t){
				Platform.runLater(new Runnable() {
		            @Override
					public void run() {
		            	mainObsList.setAll(task.getValue());
		            	listViewDisplay.scrollTo(0);
		            } 
		        });
			}
		});		
	}
	
	@FXML
	public void showSettings(){
		setCenter(configControl);
		setRandomStatus();
	}
	
	@FXML
	public void clearList(){
		if(getCenter() == configControl){
			setCenter(listViewDisplay);
		}
		setRandomStatus();
		setInitialTableView();
	}
	
	public void setInitialTableView(){
		mainObsList.setAll(Config.getMarket().getAssetList());
		listViewDisplay.setItems(mainObsList);
		setRandomStatus();
    	listViewDisplay.scrollTo(0);
	}
	
	public void saveSettings(){
		mainObsList.setAll(Config.getMarket().getAssetList());
		listViewDisplay.setItems(mainObsList);
		setCenter(listViewDisplay);
    	listViewDisplay.scrollTo(0);
		setRandomStatus();
	}
	
	public static VaultMainControl getVaultMainControl(){
		return vaultMainControl;
	}
	
	public void setRandomStatus(){
		statusText.setText(SymbolsEnum.randomStatus());
	}
	
	public void setStatus(String txtToDisplay){
		setBottom(statusText);
		statusText.setText(txtToDisplay);
	}
	
	public void entrySelected(Entry entry){
		int i = Config.getMarket().getAssetList().indexOf(entry.getAsset());
		List<Exit> entryStatusList = Config.getMarket().getAssetList().get(i).getEntryStatusList(new DigitalSpeculator());
		Exit exitToSelect = entryStatusList.get(0);
		for(Exit exit : entryStatusList){
			if(entry.getLocationIndex() == exit.getEntry().getLocationIndex()){
				exitToSelect = exit;
			}
		}
		mainObsList.setAll(entryStatusList);
		listViewDisplay.getSelectionModel().select(exitToSelect);
	}
	
	public void exitSelected(Exit exit){
		int i = Config.getMarket().getAssetList().indexOf(exit.getEntry().getAsset());
		List<Entry> entryList = Config.getMarket().getAssetList().get(i).getEntryList(new DigitalSpeculator());
		Entry entryToSelect = entryList.get(0);
		for(Entry entry : entryList){
			if(entry.getLocationIndex() == exit.getEntry().getEntryIndex()){
				entryToSelect = entry;
			}
		}
		mainObsList.setAll(entryList);
		listViewDisplay.getSelectionModel().select(entryToSelect);
	}
	
		@FXML
	public void onKeyPressed(KeyEvent event){
		KeyCode kc = event.getCode();
		if (kc == KeyCode.SPACE) {
			Displayable displayable = listViewDisplay.getSelectionModel().getSelectedItem();
			if(displayable!=null){
				if(displayable instanceof Asset){
					showNewEntries();
				}else if(displayable instanceof Entry){
					entrySelected((Entry) displayable);
				}else if(displayable instanceof Exit && ((Exit) displayable).isExit()){
					exitSelected((Exit) displayable);
				}else if(displayable instanceof Exit && ((Exit) displayable).isOpen()){
					exitSelected((Exit) displayable);
				}else{
					
				}
			}else{
				//pop up?
			}
        }
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
        vaultMainControl = this;
		configControl = new ConfigControl();
		setInitialTableView();
        setRandomStatus();		
	}
	
	public void disableAllBtns(){
		newEntriesBtn.setDisable(true);		
		showCloseBtn.setDisable(true);	
		settingsBtn.setDisable(true);		
		clearListBtn.setDisable(true);		
		backTestBtn.setDisable(true);		
	}
	
	public void enableAllBtns(){
		newEntriesBtn.setDisable(false);		
		showCloseBtn.setDisable(false);	
		settingsBtn.setDisable(false);		
		clearListBtn.setDisable(false);		
		backTestBtn.setDisable(false);		
	}

}
