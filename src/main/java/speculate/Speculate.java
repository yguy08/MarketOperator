package speculate;

import java.math.BigDecimal;
import vault.Vault;

public interface Speculate {

	String BACK_TEST = "Back Test";
	String LIVE = "Live";
	
	String POLONIEX_OFFLINE   = "Poloniex Offline Market";
	
	String LONG = "Long";
	String SHORT = "Short";
	
	BigDecimal STOCK_EQUITY = new BigDecimal(10_000.00); 
	BigDecimal DIGITAL_EQUITY = new BigDecimal(5.00000000);
	
	BigDecimal RISK = new BigDecimal(0.01);
	BigDecimal STOP = new BigDecimal(2.00);
	
	BigDecimal VOLUME_FILTER = new BigDecimal(10.00);
	
	int ENTRY = 25;
	int EXIT  = 10;
	int MOVING_AVG = 20;
	
	int MAX_UNITS = 6;
	
	int DAYS = 365 * 2;
	
	boolean LONG_FILTER = true;
	
	void setAccountEquity(BigDecimal tradeResult);
	
	BigDecimal getAccountEquity();
	
	void setTotalReturnPercent();
	
	BigDecimal getTotalReturnPercent();
	
	void getAllOpenPositions(Vault vault, Speculate speculate);
	
	void getNewEntries(Vault vault, Speculate speculate);
	
	void getPositionsToClose(Vault vault, Speculate speculate);

	void runBackTest(Vault vault, Speculate speculate);
	
	int getEntryDays();
	
	int getExitDays();
	
	int getMaxUnits();
	
	BigDecimal getRisk();
	
	BigDecimal getStop();
	
	Speculate copy(Speculate speculate);
}
