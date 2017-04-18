package entry;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import speculate.Speculate;

public interface Entry {
	
	void setEntry();
		
	String getDate();
		
	BigDecimal getCurrentPrice();
	
	void setMaxPrice(List<?> priceSubList);
	
	BigDecimal getMaxPrice();
	
	void setMinPrice(List<?> priceSubList);
	
	BigDecimal getMinPrice();
	
	String getDirection();
	
	void setDirection(String direction);
	
	void setLocationAsIndex();
	
	int getLocationIndex();
	
	Boolean isEntry();
	
	void setPriceSubList();
	
	void setTrueRange();
	
	void setTrueRange(BigDecimal trueRange);
	
	BigDecimal getTrueRange();
	
	void setStop();
	
	void setStop(BigDecimal stop);
	
	void setCurrentPrice(BigDecimal currentPrice);
	
	BigDecimal getStop();
	
	void setUnitSize(Speculate speculate);
	
	BigDecimal getUnitSize();
	
	void setOrderTotal();
	
	BigDecimal getOrderTotal();

	Date getDateTime();
	
	BigDecimal getVolume();
	
	boolean isLong();
	
	Entry copy(Entry entry, Speculate speculate);
	
	void setAssetName(String assetName);
	
	String getAssetName();
	
	void setVolume(BigDecimal volume);
	
	void setDate(String date);

}
