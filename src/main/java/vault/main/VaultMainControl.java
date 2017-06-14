package vault.main;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import asset.Asset;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import market.Market;
import price.Entry;
import price.Exit;
import speculator.Speculator;
import speculator.SpeculatorControl;
import trade.Trade;
import util.Tuple;
import vault.listview.EntryListViewControl;
import vault.listview.ExitListViewControl;
import vault.listview.MainListViewControl;

public class VaultMainControl extends BorderPane implements Initializable {
	
	private Market market;
	
	private static VaultMainControl vaultMainControl;
	
	@FXML private MainListViewControl mainListViewControl;
	
	private EntryListViewControl entryListViewControl;
	
	private ExitListViewControl exitListViewControl;
	
	private SpeculatorControl speculatorControl;
	
	@FXML private Button newEntriesBtn;
	
	@FXML private Button showCloseBtn;
	
	@FXML private Button showOpenBtn;
	
	@FXML private Button settingsBtn;
	
	@FXML private Button clearList;
	
	@FXML private Text statusText;
	
	@FXML private Button backTest;
    
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
		clearList();
		Speculator speculator = speculatorControl.getSpeculator();
		List<Asset> assetList = market.getAssetList();
		Task<List<Entry>> task = new Task<List<Entry>>() {
		    @Override protected List<Entry> call() throws Exception {
		    	List<Entry> entryList = new ArrayList<>();
				for(Asset asset : assetList){
					List<Entry> assetEntryList = asset.getEntryList(speculator);
					for(Entry e : assetEntryList){
						entryList.add(e);
					}
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
				List<Entry> entryList = task.getValue();
				Platform.runLater(new Runnable() {
		            public void run() {
		            	setCenter(entryListViewControl);		         
			            entryListViewControl.setList(entryList);
		            }
		        });
			}
		});
     }
	
	@FXML public void showNewExits(){
		clearList();
		Speculator speculator = speculatorControl.getSpeculator();
		Task<List<Exit>> task = new Task<List<Exit>>() {
		    @Override protected List<Exit> call() throws Exception {
				List<Exit> exitList = new ArrayList<>();
				for(Asset a : market.getAssetList()){
					for(Exit e : a.getExitList(speculator)){
						exitList.add(e);
					}
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
				List<Exit> exitList = task.getValue();
				Platform.runLater(new Runnable() {
		            public void run() {
		            	setCenter(exitListViewControl);
		            	exitListViewControl.setList(exitList);
		            }
		        });
			}
		});	
	}
	
	@FXML public void backtest(){
		clearList();
		Speculator speculator = speculatorControl.getSpeculator();

		//get exit list
		Task<List<String>> task = new Task<List<String>>() {
		    @Override protected List<String> call() throws Exception {
				List<Exit> exitList = new ArrayList<>();
				for(Asset a : market.getAssetList()){
					for(Exit e : a.getEntryStatusList(speculator)){
						exitList.add(e);
					}
				}
				
				//sort list
				Collections.sort(exitList, new Comparator<Exit>() {
				    @Override
					public int compare(Exit exit1, Exit exit2) {
				        return exit1.getEntryDate().compareTo(exit2.getEntryDate());
				    }
				});
				
				Trade t = new Trade(speculator);
				t.setEntryExitList(exitList);
				List<String> resultsList = t.runBackTest();
				
		       return resultsList;		      
		    }
		};
		
		new Thread(task).start();
		task.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
			@Override
			public void handle(WorkerStateEvent t){
				List<String> resultsList = task.getValue();
				Platform.runLater(new Runnable() {
		            public void run() {
		            	setCenter(mainListViewControl);
		            	mainListViewControl.setList(resultsList);
		            } 
		        });
			}
		});		
	}
	
	@FXML
	public void showSettings(){
		setCenter(speculatorControl);
		setRandomStatus();
	}
	
	@FXML
	public void clearList(){
		entryListViewControl.clearList();
		exitListViewControl.clearList();
		mainListViewControl.clearList();
		setRandomStatus();
	}
	
	public void setInitialTableView(){
		List<String> assetList = new ArrayList<>();
		for(Asset asset : market.getAssetList()){
			assetList.add(asset.toString());
		}
		setCenter(mainListViewControl);
		mainListViewControl.setList(assetList);
		setRandomStatus();
	}
	
	public void setMarket(Market market) {
		this.market = market;
	}
	
	public static VaultMainControl getVaultMainControl(){
		return vaultMainControl;
	}
	
	public void setRandomStatus(){
		statusText.setText(StatusEnum.randomStatus());
	}
	
	public void entrySelected(Entry entry){
		clearList();
		int i = market.getAssetList().indexOf(entry.getAsset());
		Asset asset = market.getAssetList().get(i);
		setCenter(exitListViewControl);
		exitListViewControl.setList(asset.getEntryStatusList(speculatorControl.getSpeculator()));
	}
	
	public void exitSelected(Exit exit){
		clearList();
		setCenter(entryListViewControl);
		showNewEntries();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
        vaultMainControl = this;
		entryListViewControl = new EntryListViewControl();
		exitListViewControl = new ExitListViewControl();
		speculatorControl = new SpeculatorControl();
        setRandomStatus();		
	}

}
