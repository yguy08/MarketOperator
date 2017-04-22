package asset;

import market.Market;

public class AssetFactory {
	
	public Asset createAsset(Market market, String assetName){
		switch(market.getMarketName()){
		case Market.DIGITAL_MARKET:
			return new DigitalAsset(market, assetName);
		default:
			return new DigitalAsset(market, assetName);
		}
	}

}
