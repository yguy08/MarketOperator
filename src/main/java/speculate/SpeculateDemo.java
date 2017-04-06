package speculate;

import asset.Asset;
import asset.AssetFactory;
import market.Market;
import market.MarketFactory;

public class SpeculateDemo {
	
	public static void main(String[] args){
	
	MarketFactory marketFactory = new MarketFactory();
	
	Market market = marketFactory.createMarket(Market.DIGITAL_MARKET);
	
	AssetFactory assetFactory = new AssetFactory();
	
	Asset asset = assetFactory.createAsset(market, "GAME/BTC");
	
	SpeculateFactory speculateFactory = new SpeculateFactory();
	
	Speculate speculate  = speculateFactory.startSpeculating(market);
	
	//WORK HERE!!!
	//speculate.getAllEntriesSingleMarket(market);
	//TODO
	//view things from speculation class. Right now some logic is in backtest and position, would like to move this to speculate class
	//instead of updating account and seeing things from comments in back test, need back test to take hold itself...
	
	speculate.backTestSingleAsset(market,asset);
	
	//speculate.backTestAllAssetsSingleMarket(market);
	
	}

}
