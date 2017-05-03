package market;

import java.util.List;

import asset.Asset;

public interface Market {
	
	public static final String DIGITAL_MARKET = "Digital Market";
	public static final String STOCK_MARKET   = "Stock Market";
	public static final String DIGITAL_OFFLINE   = "Digital Offline Market";
		
	String getMarketName();
	
	void setMarketName(String marketName);
	
	public List<Asset> getAssetList();

	public void setAssetList();
	
	public void setOfflineAssetList();
	
	
}
