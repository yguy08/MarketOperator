package position;

import java.math.BigDecimal;

public interface Position {
	
	void setExit();
	
	Boolean isOpen();
	
	void setProfitLossPercent();
	
	BigDecimal getProfitLossPercent();
	
	void setProfitLossAmount();
	
	BigDecimal getProfitLossAmount();
	
	void setPriceSubList();
	
	void setDate();
	
	String getDate();
	
	void setCurrentPrice();
	
	void setMaxPrice();
	
	void setMinPrice();
	
	void setLocationAsIndex();
	
	int getLocationIndex();
	
}
