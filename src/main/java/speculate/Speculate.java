package speculate;

import java.math.BigDecimal;

public interface Speculate {

	String BACK_TEST = "Back Test";
	String LIVE = "Live";
	
	BigDecimal STOCK_EQUITY = new BigDecimal(10_000.00); 
	BigDecimal DIGITAL_EQUITY = new BigDecimal(3.00000000);
	
	BigDecimal RISK = new BigDecimal(0.01);
	
	BigDecimal STOP = new BigDecimal(2.00);
	
	int ENTRY = 25;
	int EXIT  = 10;
	
	int MOVING_AVG = 20;
	
	void setAccountEquity(BigDecimal tradeResult);
	
	BigDecimal getAccountEquity();
	
	void getAllEntries();
	
}
