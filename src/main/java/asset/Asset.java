package asset;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;

import market.Market;

public interface Asset {

	void setAssetName(String assetName);

	String getAssetName();
	
	void setPriceList();
				
	void setOfflinePriceList();	

	List<PoloniexChartData> getPriceList();
	
	void setMarketDataService(Market market);

	void setPriceSubList(int start, int end);

	List<PoloniexChartData> getPriceSubList();
	
	//helper methods (single stat)
	
	int getIndexOfCurrentRecord();
	
	BigDecimal getClosePriceFromIndex(int index);
	
	BigDecimal getHighPriceFromIndex(int index);
	
	BigDecimal getLowPriceFromIndex(int index);
	
	String getDateStringFromIndex(int index);
	
	Date getDateTimeFromIndex(int index);
	
	BigDecimal getVolumeFromIndex(int index);
	
	//help methods (lists)	
	List<BigDecimal> getClosePriceListFromSubList();
	

}
