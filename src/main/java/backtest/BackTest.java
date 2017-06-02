package backtest;

import java.util.Date;
import java.util.List;

import entry.Entry;
import market.Market;
import position.Position;
import speculator.Speculator;

public interface BackTest {
	
	void runBackTest();
	
	void protoBackTest();
	
	void setEntryList(Entry entry);
	
	List<Entry> getEntryList();
	
	void setPositionList(Position position);
	
	List<Position> getPositionList();
	
	Entry getLastEntry();
	
	Position getLastPosition();
	
	void setSortedEntryList(List<Entry> entryList);
	
	List<Entry> getSortedEntryList();
	
	void setSortedPositionList(List<Position> positionList);
	
	List<Position> getSortedPositionList();
	
	void addUnit(Entry entry);
	
	void subtractUnit(Position position);
	
	List<String> getResultsList();
	
	String getStartBackTestToString();
	
	void setStartDate(Date date);
	
	String getStartDate();
	
	void dataSetUp();
	
	/*
	 * Get entries that are at or above entry flag param
	 */
	void getEntriesAtOrAboveEntryFlag(Market market, Speculator speculator);
	
	/*
	 * Get assets that are at exit flag param.
	 */
	void getAssetsAtExitFlag(Market market, Speculator speculator);
}
