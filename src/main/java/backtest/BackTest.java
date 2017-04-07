package backtest;

import java.util.List;

import asset.Asset;
import entry.Entry;
import position.Position;

public interface BackTest {
	
	void runBackTest();
	
	void setEntryList(Entry entry);
	
	List<Entry> getEntryList();
	
	void setPositionList(Position position);
	
	List<Position> getPositionList();
	
	Entry getLastEntry();
	
	Position getLastPosition();
	
	Asset getAsset();
	
}
