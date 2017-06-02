package position;

import java.math.BigDecimal;
import java.util.Date;

import entry.Entry;

public interface Position {
	
	void setExit();
	
	Boolean isOpen();
	
	void setAssetName(String assetName);
	
	void setProfitLossPercent();
	
	BigDecimal getProfitLossPercent();
	
	void setProfitLossAmount(Entry entry);
	
	BigDecimal getProfitLossAmount();
	
	void setPriceSubList();
	
	void setDate();
	
	String getDate();
	
	void setCurrentPrice();
	
	void setMaxPrice();
	
	void setMaxPrice(BigDecimal maxPrice);
	
	void setMinPrice(BigDecimal minPrice);
	
	void setMinPrice();
	
	BigDecimal getMaxPrice();
	
	BigDecimal getMinPrice();
	
	void setOpen(boolean isOpen);
	
	String getAssetName();
	
	void setLocationAsIndex();
	
	int getLocationIndex();
	
	Date getDateTime();

	Entry getEntry();
	
	void setCurrentPrice(BigDecimal currentPrice);
	
	BigDecimal getCurrentPrice();

	void setProfitLossPercent(Position position);

	void setEntryDate(String date);
	
	String getEntryDate();
	
	void setEntryPrice(BigDecimal entryPrice);
	
	BigDecimal getEntryPrice();
	
	void setDate(String date);
	
}
