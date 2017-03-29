package position;

import java.math.BigDecimal;

public interface Position {
	
	BigDecimal getEntryPrice();
	
	void setEntryPrice();
	
	int getEntryDate();
	
	void setEntryDate();
	
	BigDecimal getAverageTrueRange();
	
	void setAverageTrueRange();
	
	BigDecimal getExitPrice();
	
	void setExitPrice();
	
	int getExitDate();
	
	void setExitDate();
	
	void setProfitLoss();
	
	BigDecimal getProfitLoss();
	
	
}
