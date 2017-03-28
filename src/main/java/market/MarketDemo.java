package market;

public class MarketDemo {

	public static void main(String[] args) {
		
		MarketFactory marketFactory = new MarketFactory();
		
		Market market = marketFactory.createMarket(Market.STOCK_MARKET);
		
		System.out.println(market.getName());
		
		for(String assets : market.getAssets()){
			System.out.println(assets);
		}
		
		System.out.println(market.getExchangeName());
		
		System.out.println(market.getSingleAsset("FB"));

	}

}
