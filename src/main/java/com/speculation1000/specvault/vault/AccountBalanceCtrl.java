package com.speculation1000.specvault.vault;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TreeMap;

import org.knowm.xchange.currency.Currency;

import com.speculation1000.specdb.dao.MarketDAO;
import com.speculation1000.specdb.db.DbConnectionEnum;
import com.speculation1000.specdb.db.DbUtils;
import com.speculation1000.specdb.market.AccountBalance;
import com.speculation1000.specdb.market.ExchangeEnum;
import com.speculation1000.specdb.market.Market;
import com.speculation1000.specdb.market.Symbol;
import com.speculation1000.specdb.start.SpecDbException;

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

public class AccountBalanceCtrl extends BorderPane implements Initializable, Displayable {
	
	@FXML private ListView<String> listView;
	
	private ObservableList<String> mainObsList = FXCollections.observableArrayList();
	
	@FXML private ListView<Market> listViewM;
	
	private ObservableList<Market> mainObsListM = FXCollections.observableArrayList();

	public AccountBalanceCtrl() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AccountBalView.fxml"));
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
		DbConnectionEnum dbce = DbConnectionEnum.H2_MAIN;
    	try {
    		List<AccountBalance> accountBalList = DbUtils.getLatestAccountBalances(dbce);
    		
    		TreeMap<Symbol, BigDecimal> closeMap = MarketDAO.getCurrentCloseMap(dbce);
    		for(ExchangeEnum e : ExchangeEnum.values()){
    			BigDecimal tot = new BigDecimal(0.00);
    			for(AccountBalance ab : accountBalList){
    				if(ab.getExchange().equals(e.getExchangeSymbol())){
    					if(!ab.getCounter().equalsIgnoreCase("BTC")){
    						BigDecimal multiplier = closeMap.get(new Symbol(ab.getCounter(),
    								Currency.BTC.toString(),ab.getExchange()));
    						tot = tot.add(ab.getAmount().multiply(multiplier));
    					}else{
    						tot = tot.add(ab.getAmount());
    					}
    				}
    			}
    			mainObsList.add(e.getExchangeSymbol() + " " + tot.toString());
    		}
		} catch (SpecDbException e) {
			e.printStackTrace();
		}
    	
    	listView.setItems(mainObsList);
    	listView.scrollTo(0);
		listView.getSelectionModel().select(0);
	}

	@Override
	@FXML public void navUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	@FXML public void navDown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	@FXML public void navA() {
		showBalance();
		
	}

	@Override
	public void navB() {
		//VaultMainControl.getVaultMainControl().mainSetUp();
	}
	
	public void showBalance(){
        Task<List<AccountBalance>> task = new Task<List<AccountBalance>>() {
            @Override
            protected List<AccountBalance> call() throws Exception {
            	List<AccountBalance> lst = new ArrayList<>();
            	List<AccountBalance> accountBalList = DbUtils.getLatestAccountBalances(DbConnectionEnum.H2_MAIN);
            	for(AccountBalance ab : accountBalList){
            		Symbol sb;
            		if(!ab.getCounter().equalsIgnoreCase("BTC")){
            			sb = new Symbol(ab.getCounter(),Currency.BTC.toString(),ab.getExchange());
            			lst.add(ab);
            		}else{
            			sb = new Symbol(Currency.BTC.toString(),Currency.USDT.toString(),ab.getExchange());
            			lst.add(ab);
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
		            	List<String> tmpLst = new ArrayList<>();
		            	for(AccountBalance ab : task.getValue()){
		            		tmpLst.add(ab.toString());
		            	}
		        		mainObsList.setAll(tmpLst);
		            }
		        });
			}
		});
	}

}
