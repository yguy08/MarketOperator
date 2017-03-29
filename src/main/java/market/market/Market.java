package market;

import java.util.List;

public interface Market {
	
	public static final String DIGITAL_MARKET = "Digital Market";
	public static final String STOCK_MARKET   = "Stock Market";
	
	public String getMarketName();
	
	public void setAllAssets();
	
	public <E> List<E> getAllAssets();
	
	void setAsset(String assetName);
	
	String getAsset();
	
	public void setExchange();
	
	public String getExchangeName();	
	
	public void setDataService();
	
	public <E> Object getDataService();
	
	
}
