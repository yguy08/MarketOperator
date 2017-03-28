package exchange;

import market.Market;

public class ExchangeFactory {
	
	public Exchange createExchange(String marketName){
		if(marketName == null){
			return null;
		}
		if(marketName.equalsIgnoreCase(Market.STOCK_MARKET)){
			return new StockExchange();
		}else if (marketName.equalsIgnoreCase(Market.DIGITAL_MARKET)){
			return new DigitalExchange();
		}
		
		return null;
	}

}
