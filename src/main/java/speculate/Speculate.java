package speculate;

import java.math.BigDecimal;
import java.util.List;

import entry.Entry;
import position.Position;
import vault.Vault;

public interface Speculate {

	String BACK_TEST = "Back Test";
	String LIVE = "Live";
	
	String LONG = "Long";
	String SHORT = "Short";
	
	BigDecimal STOCK_EQUITY = new BigDecimal(10_000.00); 
	BigDecimal DIGITAL_EQUITY = new BigDecimal(10.00000000);
	
	BigDecimal RISK = new BigDecimal(0.02);
	BigDecimal STOP = new BigDecimal(1.00);
	
	int ENTRY = 25;
	int EXIT  = 10;
	int MOVING_AVG = 20;
	
	int MAX_UNITS = 4;
	
	void setAccountEquity(BigDecimal tradeResult);
	
	BigDecimal getAccountEquity();
	
	void setTotalReturnPercent();
	
	BigDecimal getTotalReturnPercent();
	
	void getAllOpenPositions(Vault vault, Speculate speculate);
	
	void getNewEntries(Vault vault, Speculate speculate);
	
	void getPositionsToClose(Vault vault, Speculate speculate);

	void runBackTest(Vault vault, Speculate speculate);
	
}
