package market;

public class MarketFactory {
	
	public Market createMarket(String marketName){
		Market market;
		switch(marketName){
		case Market.DIGITAL_MARKET:
			market = new DigitalMarket();
			break;
		default:
			market = null;
		}
		
		return market;
		
	}
	
}
