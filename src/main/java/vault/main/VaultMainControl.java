package vault.main;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;

import asset.Asset;
import backtest.BackTest;
import backtest.BackTestFactory;
import entry.Entry;
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
import position.Position;
import speculator.Speculator;
import speculator.SpeculatorControl;
import util.DateUtils;
import vault.listview.MainListViewControl;

public class VaultMainControl extends BorderPane implements Initializable {
	
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
	
	private List<Entry> entryList;
	
	private List<Entry> exitList;
	
	//think this is helpful...try and implement
	private HashMap<Entry, Position> entryClosePair;
    
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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mainListViewControl = new MainListViewControl();
		speculatorControl = new SpeculatorControl();
		entryList = new ArrayList<Entry>();
		setCenter(mainListViewControl);
		vaultMainControl = this;
		setRandomStatus();
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
				BackTest backtest = BackTestFactory.runBackTest(market, speculator);
				backtest.getEntriesAtOrAboveEntryFlag(market, speculator);
		        return backtest.getEntryList();
		    }
		};
		
		new Thread(task).start();
		
		task.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
			@Override
			public void handle(WorkerStateEvent t){
				entryList = task.getValue();
				Platform.runLater(new Runnable() {
		            public void run() {
		            	//EntryListViewControl entryLstView = new EntryListViewControl();
		            	setCenter(mainListViewControl);
			            for(int i = 0; i < entryList.size(); i++){
			            	int days = DateUtils.getNumDaysFromDateToToday(entryList.get(i).getDateTime());
			            	if(i != 0 && days <= speculator.getTimeFrameDays()){
			            		//issue with how date is calculated during est - utc 8 pm - midnight window...
			            		//Date last = DateUtils.dateToUTCMidnight(entryList.get(i).getDateTime());
			            		boolean isSameDayAsPrev = (DateUtils.getNumDaysFromDateToToday(entryList.get(i).getDateTime())) == 
			            							  (DateUtils.getNumDaysFromDateToToday(entryList.get(i-1).getDateTime()));
			            		if(isSameDayAsPrev){
			            			mainListViewControl.getMainObservableList().add(entryList.get(i).toString());
			            		}else {
			            			mainListViewControl.getMainObservableList().add(DateUtils.dateToMMddFormat(entryList.get(i).getDateTime()));
			            			mainListViewControl.getMainObservableList().add(entryList.get(i).toString());
			            		}
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
				BackTest backtest = BackTestFactory.runBackTest(market, speculator);
				backtest.getEntriesAtExitFlag(market, speculator);
				return backtest.getExitList();
		    }
		};
		
		new Thread(task).start();
		task.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
			@Override
			public void handle(WorkerStateEvent t){
				exitList = task.getValue();
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
	
	public List<Entry> getEntryList(){
		return entryList;
	}

}
