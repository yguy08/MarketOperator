package trade;

import java.util.Date;
import java.util.List;

import entry.Entry;
import position.Position;

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
}
