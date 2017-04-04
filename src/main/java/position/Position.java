package position;

import java.math.BigDecimal;

import asset.Asset;

public interface Position {
	
	void setExit();
	
	Boolean isOpen();
	
	void setProfitLoss();
	
	void setPriceSubList();
	
	void setDate();
	
	void setCurrentPrice();
	
	void setMaxPrice();
	
	void setMinPrice();
	
	void setLocationAsIndex();
	
	int getLocationIndex();
	
	void updateAccountBalance();
	
}
