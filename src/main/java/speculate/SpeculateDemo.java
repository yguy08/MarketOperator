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
	
	Asset asset = assetFactory.createAsset(market, "XMR/BTC");
	
	SpeculateFactory speculateFactory = new SpeculateFactory();
	
	Speculate speculate  = speculateFactory.startSpeculating(market);
	
	//speculate.getLastEntrySingleMarket(market);
	
	//speculate.getLastPositionSingleMarket(market);
	
	//speculate.getCurrentEntriesSingleMarket(market);
	
	//speculate.getPositionsToCloseSingleMarket(market);
	
	//speculate.backTestAllAssetsSingleMarket(market);
	
	//speculate.backTestSingleAsset(market, asset);
	
	//to-do...add close to position list. figure out longs only and backtest...
	
	}

}
