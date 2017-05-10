package asset;

import java.math.BigDecimal;
import java.util.List;

import market.Market;

public interface Asset {

	void setAssetName(String assetName);
	
	String getAsset();
	
	void setPriceList();
	
	List<?> getPriceList();
	
	void setCloseList();
	
	List<BigDecimal> getCloseList();
	
	void setLowList();
	
	List<BigDecimal> getLowList();
	
	void setHighList();
	
	List<BigDecimal> getHighList();
	
	void setPriceSubList(int start, int end);
	
	List<?> getPriceSubList();
	
	String getAssetName();
	
	String getMarketName();
	
	void setMarketName(String marketName);	
	
	void setOfflinePriceList();
	
	void setMarketDataService(Market market);

}
