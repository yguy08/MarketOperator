package backtest;

import market.Market;
import speculator.Speculator;

public class BackTestFactory {
	
	//...
	public static BackTest runBackTest(Market market, Speculator speculator){
		return new DigitalBackTest(market, speculator);
	}
}

