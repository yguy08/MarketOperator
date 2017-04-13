package vault;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import market.Market;
import market.MarketFactory;

public class Vault {
	
	public Market market;
	public ObservableList<String> resultsList = FXCollections.observableArrayList();;
	
	public Vault(String marketName, ObservableList<String> resultsList){
		MarketFactory marketFactory = new MarketFactory();
		this.market = marketFactory.createMarket(marketName);
		this.resultsList = resultsList;
	}
	
	public Vault(String marketName){
		MarketFactory marketFactory = new MarketFactory();
		this.market = marketFactory.createMarket(marketName);
	}

}
