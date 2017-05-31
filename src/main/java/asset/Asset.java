package asset;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;

import market.Market;

public interface Asset {

	void setAssetName(String assetName);
	
	String getAsset();
	
	void setAssetPriceList();
	
	List<?> getAssetPriceList();
	
	void setCloseList();
	
	List<BigDecimal> getCloseList();
	
	void setLowList();
	
	List<BigDecimal> getLowList();
	
	void setHighList();
	
	List<BigDecimal> getHighList();
	
	void setAssetPriceSubList(int start, int end);
	
	List<?> getAssetPriceSubList();
				
	void setOfflinePriceList();
	
	void setMarketDataService(Market market);

	String getAssetName();

	Date getDate(int index);

	BigDecimal getClose(int index);

	void setOfflineAssetPriceList();

	void setPriceList();

	List<PoloniexChartData> getPriceList();

	void setPriceSubList(int start, int end);

	List<PoloniexChartData> getPriceSubList();

	String getMarketName();

	void setMarketName(String marketName);
	
	//helper methods
	BigDecimal getCurrentPriceFromSubList();
	
	BigDecimal getCurrentVolumeFromSubList();
	
	String getCurrentDateStringFromSubList();
	
	int getIndexOfCurrentRecordFromSubList();
	
	List<BigDecimal> getClosePriceListFromSubList();
	
	BigDecimal getClosePriceFromIndex(int index);
	
	String getDateStringFromIndex(int index);

}
