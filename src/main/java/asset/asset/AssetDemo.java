package asset;

import market.Market;
import market.MarketFactory;

public class AssetDemo {

	public static void main(String[] args) {
		
		MarketFactory marketFactory = new MarketFactory();
		
		Market market = marketFactory.setMarket(Market.STOCK_MARKET, "FB");
		
		AssetFactory assetFactory = new AssetFactory();
		
		Asset asset = assetFactory.createAsset(market);
		
		System.out.println(asset.getAsset());
		
		for(Object p : asset.getPriceList()){
			System.out.println(p.toString());
		}
		

	}

}
