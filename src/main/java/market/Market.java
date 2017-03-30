package market;

import java.util.List;

public interface Market {
	
	public static final String DIGITAL_MARKET = "Digital Market";
	public static final String STOCK_MARKET   = "Stock Market";
	
	public static final BigDecimal STOCK_EQUITY = new BigDecimal(100_000.00); 
	public static final BigDecimal DIGITAL_EQUITY = new BigDecimal(10.00000000);
	public static final BigDecimal RISK = new BigDecimal(0.01);
		
	String getMarketName();
	
	void setAssets();
	
	List getAssets();
	
	
}
