package vault.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import vault.listview.MainListViewControl;

public class VaultMainControl extends BorderPane {
	
	private Market market;
	
	private MainListViewControl mainListViewControl;
	
	private static VaultMainControl vaultMainControl;
	
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
        
        vaultMainControl = this;
		mainListViewControl = new MainListViewControl();
		speculatorControl = new SpeculatorControl();
		setCenter(mainListViewControl);
        
        try {
            fxmlLoader.load();
            setRandomStatus();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
	
	/*
	 * Show assets at or above entry flag, select to view close?
	 */
	@FXML public void showNewEntries(){
		mainListViewControl.getMainObservableList().removeAll(mainListViewControl.getMainObservableList());
		setRandomStatus();
		Speculator speculator = speculatorControl.getSpeculator();
		List<Asset> assetList = market.getAssetList();
		Task<List<Entry>> task = new Task<List<Entry>>() {
		    @Override protected List<Entry> call() throws Exception {
		    	List<Entry> entryList = new ArrayList<>();
				for(Asset asset : assetList){
					List<Entry> assetEntryList = asset.getEntryList(speculatorControl.getSpeculator());
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
		            	setCenter(mainListViewControl);
			            for(int i = 0; i < entryList.size(); i++){
			            	int days = DateUtils.getNumDaysFromDateToToday(entryList.get(i).getDateTime());
			            	if(i != 0 && days <= speculator.getTimeFrameDays()){
			            		//issue with how date is calculated during est - utc 8 pm - midnight window...
			            		boolean isSameDayAsPrev = (DateUtils.getNumDaysFromDateToToday(entryList.get(i).getDateTime())) == 
			            							  (DateUtils.getNumDaysFromDateToToday(entryList.get(i-1).getDateTime()));
			            		if(isSameDayAsPrev){
			            			mainListViewControl.getMainObservableList().add(entryList.get(i).toString());
			            		}else {
			            			mainListViewControl.getMainObservableList().add(DateUtils.dateToMMddFormat(entryList.get(i).getDateTime()));
			            			mainListViewControl.getMainObservableList().add(entryList.get(i).toString());
			            		}
			            	}else if(i == 0 && days < speculator.getTimeFrameDays()){
		            			mainListViewControl.getMainObservableList().add(DateUtils.dateToMMddFormat(entryList.get(i).getDateTime()));
		            			mainListViewControl.getMainObservableList().add(entryList.get(i).toString());			            		
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
			            for(int i = 0; i < exitList.size(); i++){
			            	int days = DateUtils.getNumDaysFromDateToToday(exitList.get(i).getExitDate());
			            	if(i != 0 && days <= speculator.getTimeFrameDays()){
			            		//issue with how date is calculated during est - utc 8 pm - midnight window...
			            		boolean isSameDayAsPrev = (DateUtils.getNumDaysFromDateToToday(exitList.get(i).getExitDate())) == 
			            							  (DateUtils.getNumDaysFromDateToToday(exitList.get(i-1).getExitDate()));
			            		if(isSameDayAsPrev){
			            			mainListViewControl.getMainObservableList().add(exitList.get(i).toString());
			            		}else {
			            			mainListViewControl.getMainObservableList().add(DateUtils.dateToMMddFormat(exitList.get(i).getExitDate()));
			            			mainListViewControl.getMainObservableList().add(exitList.get(i).toString());
			            		}
			            	}else if(i == 0 && days < speculator.getTimeFrameDays()){
		            			mainListViewControl.getMainObservableList().add(DateUtils.dateToMMddFormat(exitList.get(i).getExitDate()));
		            			mainListViewControl.getMainObservableList().add(exitList.get(i).toString());			            		
			            	}
			            }
		            }
		        });
			}
		});	
	}
	
	@FXML public void showOpen(){
		mainListViewControl.getMainObservableList().removeAll(mainListViewControl.getMainObservableList());
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
		            	setCenter(mainListViewControl);
			            for(int i = 0; i < openList.size(); i++){
			            	mainListViewControl.getMainObservableList().add(openList.get(i).toString());
			            }
		            }
		        });
			}
		});		
	}
	
	@FXML public void backtest(){
		mainListViewControl.getMainObservableList().removeAll(mainListViewControl.getMainObservableList());
		setRandomStatus();
		Speculator speculator = speculatorControl.getSpeculator();
		for(Asset a : market.getAssetList()){
			a.getEntriesAndExits(speculator);
		}
		
			 
	}
	
	@FXML
	public void showSettings(){
		setCenter(speculatorControl);
		setRandomStatus();
	}
	
	@FXML
	public void clearList(){
		mainListViewControl.getMainObservableList().removeAll(mainListViewControl.getMainObservableList());
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
		
		mainListViewControl.getMainObservableList().setAll(assetList);
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

}
