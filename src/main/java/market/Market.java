package market;

import java.util.List;

import org.knowm.xchange.service.marketdata.MarketDataService;

public interface Market {
	
	public static final String DIGITAL_MARKET = "Digital Market";
	public static final String STOCK_MARKET   = "Stock Market";
	
	public String getName();
	
	public void setAssets();
	
	public List<String> getAssets();
	
	public void setExchange();
	
	public String getExchangeName();
	
	public String getSingleAsset(String assetName);
	
	public void setDataService();
	
	public MarketDataService getDataService();
	
	
}
