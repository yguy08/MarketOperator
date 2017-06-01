package entry;

import java.math.BigDecimal;
import java.util.Date;
import asset.Asset;

public interface Entry {
	
	int getLocationIndex();
	
	Asset getAsset();
	
	Boolean isEntry();
	
	void setTrueRange();
	
	BigDecimal getTrueRange();
	
	void setStop();
	
	BigDecimal getStop();
	
	void setUnitSize();
	
	BigDecimal getUnitSize();
	
	void setOrderTotal();
	
	BigDecimal getOrderTotal();

	Date getDateTime();

}
