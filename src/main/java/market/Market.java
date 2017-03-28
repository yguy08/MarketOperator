package market;

import java.io.IOException;
import java.util.List;

public interface Market {
	
	public static final String DIGITAL_MARKET = "Digital Market";
	public static final String STOCK_MARKET   = "Stock Market";
	
	public String getName();
	
	public void setAssets() throws IOException;
	
	public List<String> getAssets();
	
	public void setExchange();
	
	public String getExchangeName();
	
	public String getSingleAsset(String assetName);
	
	
}
