package backtest;

import asset.Asset;
import asset.AssetFactory;
import market.Market;
import market.MarketFactory;
import speculate.Speculate;
import speculate.SpeculateFactory;

public class BackTestDemo {

	public static void main(String[] args) {

		MarketFactory marketFactory = new MarketFactory();
		
		Market market = marketFactory.createMarket(Market.DIGITAL_MARKET);
		
		AssetFactory assetFactory = new AssetFactory();
		
		Asset asset = assetFactory.createAsset(market, "GAME/BTC");
		
		SpeculateFactory speculateFactory = new SpeculateFactory();
		
		Speculate speculate = speculateFactory.startSpeculating(market, asset);
		
		BackTestFactory backTestFactory = new BackTestFactory();
		
		BackTest backtest = backTestFactory.newBackTest(market, asset, speculate);
		
		
	}

}
