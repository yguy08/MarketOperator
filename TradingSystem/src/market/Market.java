package market;

import java.util.List;

public interface Market {
	
	public static final String DIGITAL_MARKET = "Digital Market";
	public static final String STOCK_MARKET   = "Stock Market";
		
	String getMarketName();
	
	void setAssets();
	
	List getAssets();
	
	
}
