package vault;

import backtest.BackTest;
import backtest.BackTestFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import market.Market;
import market.MarketFactory;
import speculate.Speculate;
import speculate.SpeculateFactory;

public class Vault {
	
	public Market market;
	public Speculate speculate;
	public BackTest backtest;
	public ObservableList<String> resultsList = FXCollections.observableArrayList();;
	
	public Vault(String marketName, ObservableList<String> resultsList){
		MarketFactory marketFactory = new MarketFactory();
		this.market = marketFactory.createMarket(marketName);
		SpeculateFactory speculateFactory = new SpeculateFactory();
		this.speculate = speculateFactory.startSpeculating(this.market);
		BackTestFactory backTestFactory = new BackTestFactory();
		this.backtest = backTestFactory.protoBackTest(this.market, this.speculate);
		this.backtest.dataSetUp();
		this.resultsList = resultsList;
	}
	
	public Vault(String marketName){
		MarketFactory marketFactory = new MarketFactory();
		this.market = marketFactory.createMarket(marketName);
	}

}
