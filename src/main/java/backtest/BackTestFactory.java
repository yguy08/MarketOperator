package backtest;

import asset.Asset;
import market.Market;
import speculate.Speculate;

public class BackTestFactory {
	
	public BackTest newBackTest(Market market, Asset asset, Speculate speculator){
		
		if(market == null){
		return null;
		}
		
		if(market.getMarketName() == Market.STOCK_MARKET){
			return new StockBackTest(market, asset, speculator);
		}else if(market.getMarketName() == Market.DIGITAL_MARKET){
			return new DigitalBackTest(market, asset, speculator);
		}
		
		return null;
	}

}
