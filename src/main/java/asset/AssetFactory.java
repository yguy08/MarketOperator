package asset;

import market.Market;
import vault.Config;

public class AssetFactory {

	public static Asset createAsset(Market market, String assetName) {
		if(Config.isConnected()){
			return DigitalAsset.createOnlineDigitalAsset(market, assetName);
		}else{
			return DigitalAsset.createOfflineDigitalAsset(assetName);
		}
	}
	
}
