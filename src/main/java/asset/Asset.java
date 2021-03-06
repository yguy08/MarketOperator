package asset;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.currency.Currency;

import market.MarketInterface;
import price.PriceData;
import speculator.Speculator;
import trade.Entry;
import trade.Exit;
import vault.Displayable;

public interface Asset extends Displayable {

	void setAssetName(String assetName);

	String getAssetName();
	
	void setPriceList();
				
	void setOfflinePriceList();
	
	void setMarketDataService(MarketInterface market);

	void setPriceSubList(int start, int end);
	
	//helper methods (single stat)
	int getIndexOfLastRecordInSubList();
	
	int getIndexOfLastRecordInPriceList();
	
	BigDecimal getClosePriceFromIndex(int index);
	
	Date getDateTimeFromIndex(int index);
	
	BigDecimal getVolumeFromIndex(int index);
	
	BigDecimal getHighPriceFromIndex(int index);
	
	BigDecimal getLowPriceFromIndex(int index);
	
	List<PriceData> getPriceDataList();
	
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
	
	//get (x) day high
	BigDecimal getHighForExitFlag();
	
	//get (x) day low
	BigDecimal getLowForExitFlag();
	
	Currency getCurrency();
	
}
