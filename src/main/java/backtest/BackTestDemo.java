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
		
		for(int i=0;i<market.getAssets().size();i++){
		Asset asset = assetFactory.createAsset(market, market.getAssets().get(i).toString());
		
		SpeculateFactory speculateFactory = new SpeculateFactory();
		
		Speculate speculate = speculateFactory.startSpeculating(market, asset);
		
		BackTestFactory backTestFactory = new BackTestFactory();
		
		BackTest backtest = backTestFactory.newBackTest(market, asset, speculate);
		
		}
		
		
	}

}
