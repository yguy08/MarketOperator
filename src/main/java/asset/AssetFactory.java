package asset;

import market.Market;

public class AssetFactory {
	
	public Asset createAsset(Market market, String assetName){
		if(market == null){
			return null;
		}
		
		if(market.getMarketName().equals(Market.STOCK_MARKET)){
			return new StockAsset(market,assetName);
		}else if (market.getMarketName().equals(Market.DIGITAL_MARKET)){
			return new DigitalAsset(market, assetName);
		}else if (market.getMarketName().equals(Market.POLONIEX_OFFLINE)){
			return new PoloniexOfflineAsset(market, assetName);
		}
		
		return null;
	}

}
