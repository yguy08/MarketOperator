package market;

import java.util.ArrayList;
import java.util.List;

import asset.Asset;
import asset.AssetFactory;
import speculator.Speculator;

public class PoloniexOffline implements Market {
	
	//market name
	private String marketName;
	
	//list of assets
	private List<Asset> assetList = new ArrayList<>();
	
	public PoloniexOffline(){
		setMarketName(Speculator.POLONIEX_OFFLINE);
		setAssetList();
	}
	
	@Override
	public void setMarketName(String marketName) {
		this.marketName = marketName;		
	}

	@Override
	public String getMarketName() {
		return this.marketName;
	}
	
	@Override
	public List<Asset> getAssetList() {
		return assetList;
	}
	
	@Override
	public void setAssetList() {
		List<String> assetsFromMarketList = Market.getOfflineAssets(getMarketName());
		AssetFactory aFactory = new AssetFactory();
		Asset asset;
		for(String assetNames : assetsFromMarketList){
			asset = aFactory.createAsset(this, assetNames);
			assetList.add(asset);
		}
	}

	@Override
	public String toString(){
		return marketName;
	}

}
