package asset;

import market.Market;

public class AssetFactory {
	
	public Asset createAsset(Market market, String assetName){
		Asset asset;
		switch(market.getMarketName()){
		case Market.DIGITAL_MARKET:
			asset = DigitalAsset.createOnlineDigitalAsset(market, assetName);
			return asset;
		case Market.DIGITAL_OFFLINE:
			asset = DigitalAsset.createOfflineDigitalAsset(market, assetName);
			return asset;			
		default:
			asset = DigitalAsset.createOfflineDigitalAsset(market, assetName);
			return asset;
		}
	}

}
