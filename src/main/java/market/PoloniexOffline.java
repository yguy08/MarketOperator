package market;

import java.util.ArrayList;
import java.util.List;

import speculator.Speculator;

public class PoloniexOffline implements Market {
	
	private String marketName;
	private List<String> assets = new ArrayList<>();
	
	public PoloniexOffline(){
		setMarketName(Speculator.POLONIEX_OFFLINE);
		setAssets();
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
	public void setAssets() {
		List<String> assetsFromMarketList;
		assetsFromMarketList = Market.getOfflineAssets(this.marketName);
		for(int i = 0; i < assetsFromMarketList.size(); i++){
			this.assets.add(assetsFromMarketList.get(i));
		}
	}

	@Override
	public List<String> getAssets() {
		return this.assets;
	}
	
	@Override
	public String toString(){
		return this.marketName + ": " + this.assets;
	}

}
