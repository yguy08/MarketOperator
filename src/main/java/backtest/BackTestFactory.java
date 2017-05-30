package backtest;

import market.Market;
import speculator.Speculator;

public class BackTestFactory {
	
	//called for full backtest..open, close don't need to call as data is setup on load
	public BackTest runBackTest(Market market, Speculator speculator){
		return new DigitalBackTest(market, speculator);
	}
}

