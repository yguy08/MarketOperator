package vault;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import market.Market;
import market.MarketFactory;
import speculator.Speculator;
import trade.BackTest;

public class Vault {
	
	public Market market;
	public Speculator speculator;
	public BackTest backtest;
	public ObservableList<String> resultsList = FXCollections.observableArrayList();;
	
	public Vault(String marketName, ObservableList<String> resultsList){
		MarketFactory marketFactory = new MarketFactory();
		market = marketFactory.createMarket(marketName);
	}

}
