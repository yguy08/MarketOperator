package trade;

import asset.Asset;
import asset.AssetFactory;
import market.Market;
import market.MarketFactory;
import speculator.Speculator;
import speculator.SpeculatorFactory;

public class BackTestDemo {

	public static void main(String[] args) {

		MarketFactory marketFactory = new MarketFactory();
		
		Market market = marketFactory.createMarket(Market.DIGITAL_MARKET);
		
		AssetFactory assetFactory = new AssetFactory();
		
		for(int i=0;i<market.getAssets().size();i++){
		Asset asset = assetFactory.createAsset(market, market.getAssets().get(i).toString());
		
		SpeculatorFactory speculatorFactory = new SpeculatorFactory();
		
		Speculator speculator = speculatorFactory.startSpeculating(market);
		
		}
		
		
	}

}
