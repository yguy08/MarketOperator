package trade;

public class TradeFactory {
	
	public Trade startTrading(){
		return new DigitalTrade();
	}

}
