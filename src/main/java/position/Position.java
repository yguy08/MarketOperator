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
	
	void setPositionSize();
	
	BigDecimal getPositionSize();
	
	void setStop();
	
	BigDecimal getStop();
	
	void setExit();
	
	Exit getExit();
	
	Boolean isOpen();
	
}
