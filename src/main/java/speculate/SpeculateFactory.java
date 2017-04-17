package speculate;

import market.Market;

public class SpeculateFactory {
	
	public Speculate startSpeculating(Market market){
		if(market == null){
			return null;
		}
		if(market.getMarketName().equalsIgnoreCase(Market.STOCK_MARKET)){
			return new StockSpeculation(market);
		}else if(market.getMarketName().equalsIgnoreCase(Market.DIGITAL_MARKET)){
			return new DigitalSpeculation(market);
		}else if(market.getMarketName().equalsIgnoreCase(Speculate.POLONIEX_OFFLINE)){
			return new DigitalSpeculation(market);
		}
		
		return null;
	}

}
