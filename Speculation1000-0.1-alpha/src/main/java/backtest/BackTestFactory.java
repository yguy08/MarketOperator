package backtest;

import asset.Asset;
import market.Market;
import speculate.Speculate;

public class BackTestFactory {
	
	//legacy in use with stock assets
	public BackTest newBackTest(Market market, Asset asset, Speculate speculator){
		
		if(market == null){
		return null;
		}
		
		if(market.getMarketName() == Market.STOCK_MARKET){
			return new StockBackTest(market, asset, speculator);
		}else if(market.getMarketName() == Market.DIGITAL_MARKET){
			return new DigitalBackTest(market, asset, speculator);
		}else if(market.getMarketName() == Market.POLONIEX_OFFLINE){
			return new DigitalBackTest(market, speculator);
		}
		
		return null;
	}
	
	//called for full backtest..open, close don't need to call as data is setup on load
	public BackTest protoBackTest(Market market, Speculate speculator){
		switch(market.getMarketName()){
		case Market.DIGITAL_MARKET:
			return new DigitalBackTest(market, speculator);
		case Market.POLONIEX_OFFLINE:
			return new DigitalBackTest(market, speculator);
		case Market.STOCK_MARKET:
			return new StockBackTest(market, speculator);
		default:
			return new DigitalBackTest(market, speculator);
		}
	}
}
