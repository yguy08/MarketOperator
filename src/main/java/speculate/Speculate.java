package speculate;

public interface Speculate {

	String BACK_TEST = "Back Test";
	String LIVE = "Live";
	
	BigDecimal STOCK_EQUITY = new BigDecimal(100_000.00); 
	BigDecimal DIGITAL_EQUITY = new BigDecimal(10.00000000);
	
	BigDecimal RISK = new BigDecimal(0.01);
	
	int ENTRY = 25;
	int EXIT  = 10;
	
	int MOVING_AVG = 20;
	
	void run();
	
	List<Entry> getEntryList();
	
	
	
	
}
