package speculator;

import market.Market;

public class SpeculatorFactory {
	
	public Speculator startSpeculating(Market market){
		Speculator speculator;
		switch(market.getMarketName()){
		case Market.DIGITAL_MARKET:
			speculator = new DigitalSpeculator(market);
			break;
		case Market.POLONIEX_OFFLINE:
			speculator = new DigitalSpeculator(market);
			break;
		case Market.STOCK_MARKET:
			speculator = new StockSpeculator(market);
			break;
		default:
			speculator = null;
			break;
		}
		
		return speculator;
		
	}

}
