package speculate;

public interface Speculate {

	String BACK_TEST = "Back Test";
	String LIVE = "Live";
	
	int ENTRY = 25;
	int EXIT  = 10;
	
	void run();
	
	List<Entry> getEntryList();
	
	
	
	
}
