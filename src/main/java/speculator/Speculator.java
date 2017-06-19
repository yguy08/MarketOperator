package speculator;

import java.math.BigDecimal;
import java.util.List;

import trade.Entry;

public interface Speculator {
	
	public void setAccountBalance(BigDecimal amount);
	
	public BigDecimal getAccountBalance();
	
	public void setStartAccountBalance(BigDecimal amount);
	
	public BigDecimal getStartAccountBalance();
	
	public void setRisk(BigDecimal percent);
	
	public BigDecimal getRisk();
	
	public void setEntrySignalDays(int numDays);
	
	public int getEntrySignalDays();
	
	public void setSellSignalDays(int numDays);
	
	public int getSellSignalDays();
	
	public void setMaxUnits(int numUnits);
	
	public int getMaxUnits();
	
	public void setStopLength(BigDecimal multiplier);
	
	public BigDecimal getStopLength();
	
	public void setMinVolume(BigDecimal volumeLimit);
	
	public BigDecimal getMinVolume();
	
	public void setTotalReturnAmount(BigDecimal amount);
	
	public BigDecimal getTotalReturnAmount();
	
	public void setTotalReturnPercent();
	
	public BigDecimal getTotalReturnPercent();
	
	public void setTimeFrameDays(int numDays);
	
	public int getTimeFrameDays();
	
	public List<Entry> getEntryList();
	
	public void setEntryList(List<Entry> entryList);
	
	public boolean isLongOnly();
	
	public void setLongOnly(boolean isLongOnly);
	
	public boolean isSortVol();
	
	public void setSortVol(boolean sortVol);
	
	public static int getPriceHistoryYears(){
		return 10;
	}
	
	public static int getMovingAvg(){
		return 20;
	}
	
	
}
