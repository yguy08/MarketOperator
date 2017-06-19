package asset;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;

import market.Market;
import trade.Entry;
import trade.Exit;
import speculator.Speculator;

public interface Asset {

	void setAssetName(String assetName);

	String getAssetName();
	
	void setMarket(Market market);
	
	Market getMarket();
	
	void setPriceList();
				
	void setOfflinePriceList();	

	List<PoloniexChartData> getPriceList();
	
	void setMarketDataService(Market market);

	void setPriceSubList(int start, int end);
	
	//helper methods (single stat)
	int getIndexOfLastRecordInSubList();
	
	int getIndexOfLastRecordInPriceList();
	
	BigDecimal getClosePriceFromIndex(int index);
	
	Date getDateTimeFromIndex(int index);
	
	BigDecimal getVolumeFromIndex(int index);
	
	BigDecimal getHighPriceFromIndex(int index);
	
	BigDecimal getLowPriceFromIndex(int index);
	
	//get start index
	int getStartIndex(int signalDays, int timeFrameDays);
	
	//help methods (lists)	
	List<BigDecimal> getClosePriceListFromSubList();
	
	//get list of entries at or above entry flag...
	List<Entry> getEntryList(Speculator speculator);
	
	//get list of exits 
	List<Exit> getExitList(Speculator speculator);
	
	//get list of exits still open 
	List<Exit> getEntryStatusList(Speculator speculator);
	

}
