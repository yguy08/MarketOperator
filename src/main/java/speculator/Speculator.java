package speculator;

import java.math.BigDecimal;
import java.util.List;

import entry.Entry;
import position.Position;
import vault.Vault;

public interface Speculator {

	String BACK_TEST = "Back Test";
	String LIVE = "Live";
	
	String POLONIEX_OFFLINE   = "Poloniex Offline Market";
	
	String LONG = "Long";
	String SHORT = "Short";
	
	BigDecimal STOCK_EQUITY = new BigDecimal(10_000.00); 
	BigDecimal DIGITAL_EQUITY = new BigDecimal(4.00000000);
	
	BigDecimal RISK = new BigDecimal(0.01);
	BigDecimal STOP = new BigDecimal(2.00);
	
	BigDecimal VOLUME_FILTER = new BigDecimal(10.00);
	
	int ENTRY = 25;
	int EXIT  = 10;
	int MOVING_AVG = 20;
	
	int MAX_UNITS = 7;
	
	int DAYS = 365 * 1;
	
	boolean LONG_FILTER = true;
	
	void getAllOpenPositions(Vault vault, Speculator speculator);
	
	void getNewEntries(Vault vault, Speculator speculator);
	
	void getPositionsToClose(Vault vault, Speculator speculator);

	void runBackTest(Vault vault, Speculator speculator);
	
	public void setAccountBalance(BigDecimal amount);
	
	public BigDecimal getAccountBalance();
	
	public void setStartAccountBalance(BigDecimal amount);
	
	public BigDecimal getStartAccountBalance();
	
	public void setRisk(BigDecimal percent);
	
	public BigDecimal getRisk();
	
	public void setEntrySignalDays(int numDays);
	
	public int getEntrySignalDays();
	
	public void setSellSignalDays(int numDays);
	
	public int getSellSignalDays();
	
	public void setMaxUnits(int numUnits);
	
	public int getMaxUnits();
	
	public void setStopLength(BigDecimal multiplier);
	
	public BigDecimal getStopLength();
	
	public void setMinVolume(BigDecimal volumeLimit);
	
	public BigDecimal getMinVolume();
	
	public void setTotalReturnAmount(BigDecimal amount);
	
	public BigDecimal getTotalReturnAmount();
	
	public void setTotalReturnPercent();
	
	public BigDecimal getTotalReturnPercent();
	
	public void setTimeFrameDays(int numDays);
	
	public int getTimeFrameDays();
	
	public List<Position> getPositionList();
	
	public void setPositionList(List<Position> positionList);
	
	public List<Entry> getEntryList();
	
	public void setEntryList(List<Entry> entryList);
	
	
}
