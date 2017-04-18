package speculate;

import market.Market;

public class SpeculateFactory {
	
	public Speculate startSpeculating(Market market){
		Speculate speculator;
		switch(market.getMarketName()){
		case Market.DIGITAL_MARKET:
			speculator = new DigitalSpeculation(market);
			break;
		case Market.POLONIEX_OFFLINE:
			speculator = new DigitalSpeculation(market);
			break;
		case Market.STOCK_MARKET:
			speculator = new StockSpeculation(market);
			break;
		default:
			speculator = null;
			break;
		}
		
		return speculator;
		
	}

}
