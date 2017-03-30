package speculate;

import java.util.List;

import asset.Asset;
import entry.Entry;

public interface Speculate {

	String BACK_TEST = "Back Test";
	String LIVE = "Live";
	
	int ENTRY = 25;
	int EXIT  = 10;
	
	void setEntries(Asset asset);
	
	List<Entry> getEntries();
	
	
	
	
}
