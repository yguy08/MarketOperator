package market;

public class MarketDemo {

	public static void main(String[] args) {
		
		MarketFactory marketFactory = new MarketFactory();
		
		Market market = marketFactory.createMarket(Market.STOCK_MARKET);
		
		System.out.println(market.toString());
		
		//market = marketFactory.createMarket(Market.DIGITAL_MARKET);
		
		//System.out.println(market.toString());
		
	}

}
