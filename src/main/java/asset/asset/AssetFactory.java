package asset;

import market.Market;

public class AssetFactory {
	
	public Asset createAsset(Market market){
		if(market == null){
			return null;
		}
		
		if(market.getMarketName().equals(Market.STOCK_MARKET)){
			return new StockAsset(market);
		}else if(market.getMarketName().equals(Market.DIGITAL_MARKET)){
			return new DigitalAsset(market);
		}	
		
		return null;
	}

}
