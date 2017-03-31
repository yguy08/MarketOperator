package position;

import java.math.BigDecimal;

import asset.Asset;

public interface Position {
	
	void setExit();
	
	Boolean isOpen();
	
	void setProfitLoss();
	
	void setCustomUnitSize();
	
	BigDecimal getCustomUnitSize();
	
	void setPriceSubList(Asset asset);
	
	void setDate();
	
	void setCurrentPrice();
	
	void setMaxPrice();
	
	void setMinPrice();
	
	void setLocationAsIndex();
	
	int getLocationIndex();
	
}
