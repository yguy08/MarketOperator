package entry;

import java.math.BigDecimal;
import java.util.Date;
import asset.Asset;

public interface Entry {
	
	Asset getAsset();
	
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
	
	void setVolume(BigDecimal volume);

}
