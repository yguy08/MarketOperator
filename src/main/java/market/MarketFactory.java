package market;

public class MarketFactory {
	
	public Market createMarket(String marketName){
		Market market;
		switch(marketName){
		case Market.DIGITAL_MARKET:
			market = new DigitalMarket();
			break;
		case Market.POLONIEX_OFFLINE:
			market = new PoloniexOffline();
			break;
		case Market.STOCK_MARKET:
			market = new StockMarket();
			break;
		default:
			market = null;
		}
		
		return market;
		
	}
	
}
