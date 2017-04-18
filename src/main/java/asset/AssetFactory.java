package asset;

import market.Market;

public class AssetFactory {
	
	public Asset createAsset(Market market, String assetName){
		switch(market.getMarketName()){
		case Market.DIGITAL_MARKET:
			return new DigitalAsset(market, assetName);
		case Market.POLONIEX_OFFLINE:
			return new PoloniexOfflineAsset(market, assetName);
		case Market.STOCK_MARKET:
			return new StockAsset(market, assetName);
		default:
			return new PoloniexOfflineAsset(market, assetName);
		}
	}

}
