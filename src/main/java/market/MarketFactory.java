package market;

public class MarketFactory {
	Market market = null;
	
	public Market createMarket(String marketName){
		
		switch(marketName){
			case Market.DIGITAL_MARKET:
				market = DigitalMarket.createOnlineDigitalMarket();
				break;
			case Market.DIGITAL_OFFLINE:
				market = DigitalMarket.createOfflineDigitalMarket();
				break;
			default:
				market = null;
		}
		
		return market;
		
	}
	
}
