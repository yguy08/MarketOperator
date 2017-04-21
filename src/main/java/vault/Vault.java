package vault;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import market.Market;
import market.MarketFactory;
import speculator.Speculator;
import speculator.SpeculatorFactory;
import trade.BackTest;
import trade.BackTestFactory;

public class Vault {
	
	public Market market;
	public Speculator speculator;
	public BackTest backtest;
	public ObservableList<String> resultsList = FXCollections.observableArrayList();;
	
	public Vault(String marketName, ObservableList<String> resultsList){
		MarketFactory marketFactory = new MarketFactory();
		this.market = marketFactory.createMarket(marketName);
		SpeculatorFactory speculatorFactory = new SpeculatorFactory();
		this.speculator = speculatorFactory.startSpeculating(this.market);
		BackTestFactory backTestFactory = new BackTestFactory();
		this.backtest = backTestFactory.protoBackTest(this.market, this.speculator);
		this.backtest.dataSetUp();
		this.resultsList = resultsList;
	}
	
	public Vault(String marketName){
		MarketFactory marketFactory = new MarketFactory();
		this.market = marketFactory.createMarket(marketName);
	}

}
