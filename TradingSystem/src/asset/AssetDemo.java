package asset;

import market.Market;
import market.MarketFactory;

public class AssetDemo {

	public static void main(String[] args) {
		
		MarketFactory marketFactory = new MarketFactory();

		Market market = marketFactory.createMarket(Market.STOCK_MARKET);
		
		AssetFactory assetFactory = new AssetFactory();
		
		Asset asset = assetFactory.createAsset(market, "Fb");
		
		System.out.println(asset.toString());
		
		//market = marketFactory.createMarket(Market.DIGITAL_MARKET);
		
		//asset = assetFactory.createAsset(market, "XMR/BTC");
		
		//System.out.println(asset.toString());
	}

}
