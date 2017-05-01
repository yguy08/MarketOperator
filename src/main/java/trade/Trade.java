package trade;

import java.util.List;

import entry.Entry;
import market.Market;
import speculator.Speculator;

public interface Trade {
	
	//set new entries..take in a market with a speculators profile (entry days, risk, etc..)
	public void setNewEntries(Market market, Speculator speculator);
	
	//get new entries
	public List<Entry> getNewEntries();

}
