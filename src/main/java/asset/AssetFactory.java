package asset;

import market.Market;

public class AssetFactory {
	
	public Asset createAsset(Market market){
		if(market == null){
			return null;
		}
		
		if(market.getName().equals(Market.STOCK_MARKET)){
			return new StockAsset();
		}else if (market.getName().equals(Market.DIGITAL_MARKET)){
			return new DigitalAsset(market);
		}
		
		return null;
	}

}
