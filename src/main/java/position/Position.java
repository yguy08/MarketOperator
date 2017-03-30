package position;

import java.math.BigDecimal;

import exit.Exit;

public interface Position {
	
	void setTrueRange();
	
	BigDecimal getTrueRange();
	
	void setPositionSize();
	
	BigDecimal getPositionSize();
	
	void setStop();
	
	BigDecimal getStop();
	
	void setExit();
	
	Exit getExit();
	
	Boolean isOpen();
	
}
