package backtest;

import asset.Asset;
import asset.AssetFactory;
import market.Market;
import market.MarketFactory;

public class BackTestDemo {

	public static void main(String[] args) {

		MarketFactory marketFactory = new MarketFactory();
		
		Market market = marketFactory.createMarket(Market.STOCK_MARKET);
		
		AssetFactory assetFactory = new AssetFactory();
		
		Asset asset = assetFactory.createAsset(market, "FB");
		
		BackTestFactory backTestFactory = new BackTestFactory();
		
		
	}

}
