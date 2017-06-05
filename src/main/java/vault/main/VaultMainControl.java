package vault.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import asset.Asset;
import entry.Entry;
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
		Task<List<Entry>> task = new Task<List<Entry>>() {
		    @Override protected List<Entry> call() throws Exception {
		    	List<Entry> entryList = new ArrayList<>();
				for(Asset a : market.getAssetList()){
					for(Entry e : a.getEntryList(speculatorControl.getSpeculator())){
						entryList.add(e);
					}
				}
				
				//sort list
				Collections.sort(entryList, new Comparator<Entry>() {
				    @Override
					public int compare(Entry o1, Entry o2) {
				        return o1.getDateTime().compareTo(o2.getDateTime());
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
		
		Task<List<Entry>> task = new Task<List<Entry>>() {
		    @Override protected List<Entry> call() throws Exception {
				List<Entry> exitList = new ArrayList<>();
				for(Asset a : market.getAssetList()){
					for(Entry e : a.getExitList(speculator)){
						exitList.add(e);
					}
				}
				
				//sort list
				Collections.sort(exitList, new Comparator<Entry>() {
				    @Override
					public int compare(Entry o1, Entry o2) {
				        return o1.getDateTime().compareTo(o2.getDateTime());
				    }
				});
		        return exitList;
		    }
		};
		
		new Thread(task).start();
		task.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
			@Override
			public void handle(WorkerStateEvent t){
				List<Entry> exitList = task.getValue();
				Platform.runLater(new Runnable() {
		            public void run() {
		            	setCenter(mainListViewControl);
			            for(int i = 0; i < exitList.size(); i++){
			            	mainListViewControl.getMainObservableList().add(exitList.get(i).toString());
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
		
		Task<List<Entry>> task = new Task<List<Entry>>() {
		    @Override protected List<Entry> call() throws Exception {
				List<Entry> openList = new ArrayList<>();
				for(Asset a : market.getAssetList()){
					for(Entry e : a.getOpenList(speculator)){
						openList.add(e);
					}
				}
				
				//sort list
				Collections.sort(openList, new Comparator<Entry>() {
				    @Override
					public int compare(Entry o1, Entry o2) {
				        return o1.getDateTime().compareTo(o2.getDateTime());
				    }
				});
		        return openList;
		    }
		};
		
		new Thread(task).start();
		task.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
			@Override
			public void handle(WorkerStateEvent t){
				List<Entry> openList = task.getValue();
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
	
	@FXML
	public void showSettings(){
		setCenter(speculatorControl);
		setRandomStatus();
	}
	
	@FXML
	public void clearList(){
		mainListViewControl.getMainObservableList().removeAll(mainListViewControl.getMainObservableList());
		setRandomStatus();
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
