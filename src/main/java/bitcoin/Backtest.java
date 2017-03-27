package bitcoin;

public class Backtest {
	
	String results;
	public Backtest(PoloAsset asset){
		results = runBacktest(asset);
	}
	
	public String runBacktest(PoloAsset asset){
		//start at 0 -> find first entry
		
		//calculate ATR
		
		//calculate position size & N
		
		//buy
		
		//set stop 
		
		//check next day if it is at stop or at 10 day low
		//check next day, check next day...
		
		//if either of those conditions are true, close
		
		//calculate profit loss, etc.
		
		//if next day is up 1N
		//buy another unit, add to current
		
		//check if price drops by stop plus 1/2N
		
		//repeat until 4 units or close and then close...
		
		return null;
	}

}
