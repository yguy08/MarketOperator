package vault.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import asset.Asset;
import entry.Entry;
import exit.Exit;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import market.Market;
import speculator.Speculator;
import speculator.SpeculatorControl;
import util.DateUtils;
import vault.listview.EntryListViewControl;
import vault.listview.ExitListViewControl;
import vault.listview.MainListViewControl;

public class VaultMainControl extends BorderPane {
	
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

        vaultMainControl = this;
		entryListViewControl = new EntryListViewControl();
		exitListViewControl = new ExitListViewControl();
		speculatorControl = new SpeculatorControl();
        setRandomStatus();
	}
	
	/*
	 * Show assets at or above entry flag, select to view close?
	 */
	@FXML public void showNewEntries(){
		mainListViewControl.clearList();
		setRandomStatus();
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
			            for(int i = 1; i < entryList.size(); i++){
			            	//issue with how date is calculated at the end of day (8pm-midnight)		            		
			            	if(isNewDay(entryList.get(i).getDateTime(), entryList.get(i-1).getDateTime())){
			            		entryListViewControl.getMainObservableList().add(entryList.get(i));
			            	}else{
			            		entryListViewControl.getMainObservableList().add(entryList.get(i));
			            	}
			            }
		            }
		        });
			}
		});
     }
	
	@FXML public void showNewExits(){
		mainListViewControl.getMainObservableList().removeAll(mainListViewControl.getMainObservableList());
		setRandomStatus();
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
				        return exit2.getExitDate().compareTo(exit1.getExitDate());
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
		            	setCenter(mainListViewControl);
		            	mainListViewControl.getMainObservableList().add(DateUtils.dateToMMddFormat(exitList.get(0).getExitDate()));
			            for(int i = 1; i < exitList.size(); i++){
			            	if(isNewDay(exitList.get(i).getExitDate(),exitList.get(i-1).getExitDate())){
			            		mainListViewControl.getMainObservableList().add(DateUtils.dateToMMddFormat(exitList.get(i).getExitDate()));
			            		mainListViewControl.getMainObservableList().add(exitList.get(i).toString());			            		
			            	}else{
			            		mainListViewControl.getMainObservableList().add(exitList.get(i).toString());			            		
			            	}
			            }
		            }
		        });
			}
		});	
	}
	
	@FXML public void showOpen(){
		mainListViewControl.clearList();
		setRandomStatus();
		Speculator speculator = speculatorControl.getSpeculator();
		
		Task<List<Exit>> task = new Task<List<Exit>>() {
		    @Override protected List<Exit> call() throws Exception {
				List<Exit> openList = new ArrayList<>();
				for(Asset a : market.getAssetList()){
					for(Exit e : a.getOpenList(speculator)){
						openList.add(e);
					}
				}
				
				//sort list
				Collections.sort(openList, new Comparator<Exit>() {
				    @Override
					public int compare(Exit o1, Exit o2) {
				        return o1.getExitDate().compareTo(o2.getExitDate());
				    }
				});
		        return openList;
		    }
		};
		
		new Thread(task).start();
		task.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
			@Override
			public void handle(WorkerStateEvent t){
				List<Exit> openList = task.getValue();
				Platform.runLater(new Runnable() {
		            public void run() {
		            	mainListViewControl.setCenter();
		            	mainListViewControl.setList(openList);
		            }
		        });
			}
		});		
	}
	
	@FXML public void backtest(){
		mainListViewControl.clearList();
		setRandomStatus();
		Speculator speculator = speculatorControl.getSpeculator();
		
		for(Asset a : market.getAssetList()){
			for(Exit e : a.getExitList(speculator)){
				System.out.println("!" + e.getDateTime() + e.getEntry().getAsset().getAssetName() + " Entry: " + e.getEntryDate());
			}
		}
	}
	
	@FXML
	public void showSettings(){
		speculatorControl.setCenter();
		setRandomStatus();
	}
	
	@FXML
	public void clearList(){
		entryListViewControl.clearList();
		mainListViewControl.clearList();
		exitListViewControl.clearList();
		setRandomStatus();
		setInitialTableView();
	}
	
	//called from speculator control
	public void setSpeculator(){
		setCenter(mainListViewControl);
		setRandomStatus();
	}
	
	public void setInitialTableView(){
		List<String> assetList = new ArrayList<>();
		for(Asset asset : market.getAssetList()){
			assetList.add(asset.toString());
		}
		mainListViewControl.setList(assetList);
		setCenter(mainListViewControl);
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
	
	//presentation helper method..add date to list for new date..
	private boolean isNewDay(Date currentDate, Date prevDate){
		boolean isNewDay = currentDate.compareTo(prevDate) < 0 || currentDate.compareTo(prevDate) > 0;
		return isNewDay;		
	}
	
	public void viewOpenForEntry(Entry entry){
		Asset asset = market.getAssetList().get(market.getAssetList().indexOf(entry.getAsset()));
		exitListViewControl.setList(asset.getOpenList(speculatorControl.getSpeculator()));
		exitListViewControl.setCenter();
		setRandomStatus();
		System.out.println("Success!");
	}
	
	public void returnToEntries(){
		entryListViewControl.setCenter();
		showNewEntries();
		setRandomStatus();
	}

}
