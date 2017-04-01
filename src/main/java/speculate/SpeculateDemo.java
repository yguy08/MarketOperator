package speculate;

import asset.Asset;
import asset.AssetFactory;
import market.Market;
import market.MarketFactory;

public class SpeculateDemo {
	
	public static void main(String[] args){
	
	MarketFactory marketFactory = new MarketFactory();
	
	Market market = marketFactory.createMarket(Market.STOCK_MARKET);
	
	AssetFactory assetFactory = new AssetFactory();
	
	Asset asset = assetFactory.createAsset(market, "GDX");
	
	SpeculateFactory speculateFactory = new SpeculateFactory();
	
	Speculate speculate = speculateFactory.startSpeculating(Speculate.BACK_TEST, market, asset);
	
	}

}
