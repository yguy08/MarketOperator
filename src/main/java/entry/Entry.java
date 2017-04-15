package entry;

import java.math.BigDecimal;
import java.util.Date;

import asset.Asset;
import speculate.Speculate;

public interface Entry {
	
	void setEntry();
	
	void setDate();
	
	String getDate();
	
	void setCurrentPrice();
	
	BigDecimal getCurrentPrice();
	
	void setMaxPrice();
	
	BigDecimal getMaxPrice();
	
	void setMinPrice();
	
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
	
	void setVolume();
	
	boolean isLong();
	
	Entry copy(Entry entry, Speculate speculate);
	
	void setAssetName(String assetName);
	
	String getAssetName();
	
	void setVolume(BigDecimal volume);
	
	void setDate(String date);

}
