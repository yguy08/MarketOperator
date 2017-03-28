package asset;

import market.Market;
import market.MarketFactory;

public class AssetDemo {

	public static void main(String[] args) {
		
		MarketFactory marketFactory = new MarketFactory();
		
		Market market = marketFactory.createMarket(Market.STOCK_MARKET);
		
		AssetFactory assetFactory = new AssetFactory();
		
		Asset asset = assetFactory.createAsset(market);
		
		asset.setAsset(market.getSingleAsset("FB"));
		
		System.out.println(asset.getAsset());
		
		

	}

}
