package asset;

import vault.Config;

public class AssetFactory {

	public static Asset createAsset(String assetName) {
		if(Config.isConnected()){
			return DigitalAsset.createOnlineDigitalAsset(assetName);
		}else{
			return DigitalAsset.createOfflineDigitalAsset(assetName);
		}
	}
	
}
