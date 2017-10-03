package com.speculation1000.specvault.account;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TreeMap;

import org.knowm.xchange.currency.Currency;

import com.speculation1000.specdb.dao.MarketDAO;
import com.speculation1000.specdb.db.DbConnectionEnum;
import com.speculation1000.specdb.db.DbUtils;
import com.speculation1000.specdb.market.AccountBalance;
import com.speculation1000.specdb.market.ExchangeEnum;
import com.speculation1000.specdb.market.Symbol;
import com.speculation1000.specdb.start.SpecDbException;
import com.speculation1000.specvault.mainmenu.MainMenuCtrl;
import com.speculation1000.specvault.position.PositionCtrl;
import com.speculation1000.specvault.utils.SpecVaultStr;
import com.speculation1000.specvault.vault.Displayable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

public class AccountSummaryCtrl extends BorderPane implements Initializable, Displayable {
	
	@FXML private ListView<String> listView;
	
	private ObservableList<String> mainObsList = FXCollections.observableArrayList();
	
	@FXML private Button navA;
	
	@FXML private Button navB;

	public AccountSummaryCtrl() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AccountSummaryView.fxml"));
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
    		BigDecimal total = new BigDecimal(0.00);
    		for(ExchangeEnum e : ExchangeEnum.values()){
    			BigDecimal exTot = new BigDecimal(0.00);
    			for(AccountBalance ab : accountBalList){
    				if(ab.getExchange().equals(e.getExchangeSymbol())){
    					if(!ab.getCounter().equalsIgnoreCase("BTC")){
    						BigDecimal multiplier = closeMap.get(new Symbol(ab.getCounter(),
    								Currency.BTC.toString(),ab.getExchange()));
    						exTot = exTot.add(ab.getAmount().multiply(multiplier));
    					}else{
    						exTot = exTot.add(ab.getAmount());
    					}
    				}
    			}
    			total = total.add(exTot);
    			mainObsList.add(e.getExchangeSymbol() + " " + SpecVaultStr.bdToEightDecimal(exTot));
    		}
    		mainObsList.add("Total: " + SpecVaultStr.bdToEightDecimal(total));
		} catch (SpecDbException e) {
			e.printStackTrace();
		}    	
    	listView.setItems(mainObsList);
    	listView.scrollTo(0);
		listView.getSelectionModel().select(0);
	}

	@Override
	@FXML public void navUp() {
		int i = listView.getSelectionModel().getSelectedIndex();
		if(i>0) {
			listView.getSelectionModel().selectPrevious();
			listView.scrollTo(listView.getSelectionModel().getSelectedIndex());
		}
	}

	@Override
	@FXML public void navDown() {
		int i = listView.getSelectionModel().getSelectedIndex();
		if(i<listView.getItems().size()-1) {
			listView.getSelectionModel().selectNext();
			listView.scrollTo(listView.getSelectionModel().getSelectedIndex());
		}
	}

	@Override
	@FXML public void navA() {
		navA.getScene().setRoot(new PositionCtrl().getContent());
	}

	@Override
	@FXML public void navB() {
		navB.getScene().setRoot(new MainMenuCtrl().getContent());
	}

	@Override
	@FXML public void navLeft() {
				
	}

	@Override
	@FXML public void navRight() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Parent getContent() {
		return this;
	}

}
