package market;

public class MarketFactory {
	
	public Market createMarket(String marketName){
		if(marketName == null){
			return null;
		}
		
		if(marketName.equalsIgnoreCase(Market.STOCK_MARKET)){
			return new StockMarket();
		}else if (marketName.equalsIgnoreCase(Market.DIGITAL_MARKET)){
			return new DigitalMarket();
		}
		
		return null;
	}
	
	public Market setMarket(String marketName, String assetName){
		if(marketName == null){
			return null;
		}
		
		if(marketName.equalsIgnoreCase(Market.STOCK_MARKET)){
			return new StockMarket(marketName, assetName);
		}else if (marketName.equalsIgnoreCase(Market.DIGITAL_MARKET)){
			return new DigitalMarket(marketName, assetName);
		}
		
		return null;
	}
	
	
	
}
