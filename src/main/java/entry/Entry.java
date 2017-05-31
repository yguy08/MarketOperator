package entry;

import java.math.BigDecimal;
import java.util.Date;
import asset.Asset;
import speculator.Speculator;

public interface Entry {
	
	Asset getAsset();
		
	String getDate();
	
	int getLocationIndex();
	
	Boolean isEntry();
	
	void setTrueRange();
	
	void setTrueRange(BigDecimal trueRange);
	
	BigDecimal getTrueRange();
	
	void setStop();
	
	void setStop(BigDecimal stop);
	
	BigDecimal getStop();
	
	void setUnitSize();
	
	BigDecimal getUnitSize();
	
	void setOrderTotal();
	
	BigDecimal getOrderTotal();

	Date getDateTime();
	
	BigDecimal getVolume();
	
	boolean isLong();
	
	void setVolume(BigDecimal volume);
	
	void setDate(String date);

}
