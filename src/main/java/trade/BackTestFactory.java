package trade;

import asset.Asset;
import market.Market;
import speculator.Speculator;

public class BackTestFactory {
	
	//legacy in use with stock assets
	public BackTest newBackTest(Market market, Asset asset, Speculator speculator){
		
		if(market == null){
		return null;
		}
		
		if(market.getMarketName() == Market.DIGITAL_MARKET){
			return new DigitalBackTest(market, asset, speculator);
		}else if(market.getMarketName() == Market.DIGITAL_OFFLINE){
			return new DigitalBackTest(market, speculator);
		}
		
		return null;
	}
	
	//called for full backtest..open, close don't need to call as data is setup on load
	public BackTest protoBackTest(Market market, Speculator speculator){
		switch(market.getMarketName()){
		case Market.DIGITAL_MARKET:
			return new DigitalBackTest(market, speculator);
		case Market.DIGITAL_OFFLINE:
			return new DigitalBackTest(market, speculator);
		default:
			return new DigitalBackTest(market, speculator);
		}
	}
}
