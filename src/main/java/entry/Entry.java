package entry;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

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
	
	void setLocationAsIndex();
	
	int getLocationIndex();
	
	Boolean isEntry();
	
	void setPriceSubList();
	
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
