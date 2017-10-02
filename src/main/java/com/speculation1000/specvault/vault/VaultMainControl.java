package com.speculation1000.specvault.vault;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.knowm.xchange.currency.Currency;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

import com.speculation1000.specdb.dao.EntryDAO;
import com.speculation1000.specdb.dao.MarketDAO;
import com.speculation1000.specdb.db.DbConnectionEnum;
import com.speculation1000.specdb.db.DbUtils;
import com.speculation1000.specdb.market.AccountBalance;
import com.speculation1000.specdb.market.Entry;
import com.speculation1000.specdb.market.Market;
import com.speculation1000.specdb.market.Symbol;


public class VaultMainControl extends BorderPane implements Initializable {
	
	@FXML private ListView<String> listView;
	
	private ObservableList<String> mainObsList = FXCollections.observableArrayList();
    
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
	
	public void showTicker(){
        Task<List<String>> task = new Task<List<String>>() {
            @Override
            protected List<String> call() throws Exception {
            	List<String> lst = new ArrayList<>();
            	List<Market> marketList = MarketDAO.getMarketList(DbConnectionEnum.H2_MAIN, 0);
            	for(Market m : marketList){
            		lst.add(m.toString());
        		}
                return lst;
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
		        		listView.scrollTo(0);
		            }
		        });
			}
		});
	}
	
	public void showBalance(){
        Task<List<String>> task = new Task<List<String>>() {
            @Override
            protected List<String> call() throws Exception {
            	List<String> lst = new ArrayList<>();
            	List<AccountBalance> accountBalList = DbUtils.getLatestAccountBalances(DbConnectionEnum.H2_MAIN);
            	for(AccountBalance ab : accountBalList){
            		Symbol sb;
            		if(!ab.getCounter().equalsIgnoreCase("BTC")){
            			sb = new Symbol(ab.getCounter(),Currency.BTC.toString(),ab.getExchange());
            			lst.add(ab.toString()+"@"+MarketDAO.getCurrentPrice(sb).toPlainString());
            		}else{
            			sb = new Symbol(Currency.BTC.toString(),Currency.USDT.toString(),ab.getExchange());
            			lst.add(ab.toString()+"@"+MarketDAO.getCurrentPrice(sb).toPlainString());
            		}
        		}
                return lst;
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
		        		listView.scrollTo(0);
		            }
		        });
			}
		});
	}
	
	public void showMarketHighs(){
        Task<List<String>> task = new Task<List<String>>() {
            @Override
            protected List<String> call() throws Exception {
            	List<String> lst = new ArrayList<>();
            	List<Entry> entryList = EntryDAO.getMarketEntryList(DbConnectionEnum.H2_MAIN,25);
            	for(Entry e : entryList){
            		if(e.getDirection().equalsIgnoreCase("LONG")) {
                		lst.add(e.toString());
            		}
        		}
                return lst;
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
		        		listView.scrollTo(0);
		            }
		        });
			}
		});
	}
	
	public void showMarketLows(){
        Task<List<String>> task = new Task<List<String>>() {
            @Override
            protected List<String> call() throws Exception {
            	List<String> lst = new ArrayList<>();
            	List<Entry> entryList = EntryDAO.getMarketEntryList(DbConnectionEnum.H2_MAIN,25);
            	for(Entry e : entryList){
            		if(e.getDirection().equalsIgnoreCase("SHORT")) {
                		lst.add(e.toString());
            		}
        		}
                return lst;
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
		        		listView.scrollTo(0);
		            }
		        });
			}
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//config set up
		Config.configSetUp();
		
        // populate main menu
		mainSetUp();
	}
	
	@FXML 
	public void navUp(){
		int i = listView.getSelectionModel().getSelectedIndex();
		if(i>0) {
			listView.getSelectionModel().selectPrevious();
			listView.scrollTo(listView.getSelectionModel().getSelectedIndex());
		}
	}
	
	@FXML 
	public void navDown(){
		int i = listView.getSelectionModel().getSelectedIndex();
		if(i<listView.getItems().size()-1) {
			listView.getSelectionModel().selectNext();
			listView.scrollTo(listView.getSelectionModel().getSelectedIndex());
		}
	}
	
	@FXML 
	public void navA(){
		String s = listView.getSelectionModel().getSelectedItem();
		switch(s) {
		case "Ticker":
			showTicker();
			break;
		case "Account":
			showBalance();
			break;
		case "Market Highs":
			showMarketHighs();
			break;
		case "Market Lows":
			showMarketLows();
			break;
		default:
			break;
		}
	}
	
	@FXML 
	public void navB(){
		mainSetUp();
	}
	
	private void mainSetUp() {
		mainObsList.clear();
		
        // populate main menu
		mainObsList.add("Ticker");
        mainObsList.add("Account");
        mainObsList.add("Market Highs");
        mainObsList.add("Market Lows");
        mainObsList.add("Trades");
        mainObsList.add("Settings");

        listView.setItems(mainObsList);
        listView.getSelectionModel().select(0);
	}
	
}
