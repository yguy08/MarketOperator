package position;

import java.math.BigDecimal;

import exit.Exit;

public interface Position {
	
	void setTrueRange();
	
	BigDecimal getTrueRange();
	
	void setDollarVol();
	
	BigDecimal getDollarVol();
	
	void setUnitSize();
	
	BigDecimal getUnitSize();
	
	void setStop();
	
	BigDecimal getStop();
	
	void open();
	
	void setExit();
	
	Exit getExit();
	
	Boolean isOpen();
	
}
