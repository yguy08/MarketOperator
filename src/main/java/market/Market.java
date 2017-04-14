package market;

import java.util.List;

public interface Market {
	
	public static final String DIGITAL_MARKET = "Digital Market";
	public static final String STOCK_MARKET   = "Stock Market";
	public static final String POLONIEX_OFFLINE   = "Poloniex Offline Market";
		
	String getMarketName();
	
	void setAssets();
	
	List getAssets();
	
	
}
