package market;

public class MarketDemo {

	public static void main(String[] args) {
		
		MarketFactory marketFactory = new MarketFactory();
		
		Market market = marketFactory.setMarket(Market.STOCK_MARKET, "FB");
		
		System.out.println(market.getMarketName());
		
		System.out.println(market.getAsset());
		
		for(Object e : market.getAllAssets()){
			System.out.println(e.toString());
		}

	}

}
