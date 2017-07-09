package market;

public class MarketWorker implements Runnable {

	private int id;
	
	public MarketWorker(int id) {
		this.id = id;
	}

	@Override
	public void run() {
		System.out.println("Starting...");
		
		//get all asset prices
		
		//save all asset prices
		
		//find new exits
		
		//close any exits (if necessary)
		
		//find new entries
		
		//open any entries
		
		//check performance
		
		//send report...
		
		System.out.println("Completed...");
	}

}
