package speculate;

import java.math.BigDecimal;
import java.util.List;

import entry.Entry;

public interface Speculate {

	String BACK_TEST = "Back Test";
	String LIVE = "Live";
	
	BigDecimal STOCK_EQUITY = new BigDecimal(10_000.00); 
	BigDecimal DIGITAL_EQUITY = new BigDecimal(10.00000000);
	
	BigDecimal RISK = new BigDecimal(0.01);
	
	BigDecimal STOP = new BigDecimal(2.00);
	
	int ENTRY = 25;
	int EXIT  = 10;
	
	int MOVING_AVG = 20;
	
	void run();
	
	List<Entry> getEntryList();
	
	
	
	
}
