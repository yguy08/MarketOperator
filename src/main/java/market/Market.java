package market;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public interface Market {
	
	public static final String DIGITAL_MARKET = "Digital Market";
	public static final String STOCK_MARKET   = "Stock Market";
	public static final String POLONIEX_OFFLINE   = "Poloniex Offline Market";
		
	String getMarketName();
	
	void setMarketName(String marketName);
	
	void setAssets();
	
	List<?> getAssets();
	
	static void setOfflineAssets(String marketName){
		
	}
	
	static List<String> getOfflineAssets(String marketName){
		List<String> assetNames;
		
		switch(marketName){
			case POLONIEX_OFFLINE: 	
				marketName = DIGITAL_MARKET;
				break;
			default: 
				marketName = DIGITAL_MARKET;
				break;
		}
		
		try {
			assetNames = Files.readAllLines(Paths.get(marketName + "/MarketList.csv"));
			return assetNames;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	

	
	
}
