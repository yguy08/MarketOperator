package position;

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
	
	void setTradeResult();
	
	void updateAccountBalance();
	
}
