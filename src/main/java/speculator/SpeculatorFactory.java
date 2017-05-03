package speculator;

import market.Market;

public class SpeculatorFactory {
	
	public Speculator startSpeculating(Market market){
		Speculator speculator;
		switch(market.getMarketName()){
		case Market.DIGITAL_MARKET:
			speculator = DigitalSpeculator.createAverageRiskSpeculator();
			break;
		case Market.DIGITAL_OFFLINE:
			speculator = new DigitalSpeculator();
			break;
		default:
			speculator = null;
			break;
		}
		
		return speculator;
		
	}

}
