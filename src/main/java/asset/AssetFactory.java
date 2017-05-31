package asset;

import market.*;

public class AssetFactory {

	public static Asset createAsset(Market market, String assetName) {
		if(market.getMarketName().equals(MarketsEnum.BITCOIN.getMarketName())){
			return DigitalAsset.createOnlineDigitalAsset(market, assetName);
		}else if(market.getMarketName().endsWith(MarketsEnum.BITCOIN_OFFLINE.getMarketName())){
			return DigitalAsset.createOfflineDigitalAsset(market, assetName);
		}else{
			return null;
		}
	}
	
}
