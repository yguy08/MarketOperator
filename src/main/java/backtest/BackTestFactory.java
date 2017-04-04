package backtest;

import asset.Asset;
import market.Market;
import speculate.Speculate;

public class BackTestFactory {
	
	public BackTest newBackTest(Market market, Asset asset, Speculate speculate){
		
		if(market == null){
		return null;
		}
		
		if(market.getMarketName() == Market.STOCK_MARKET){
			return new StockBackTest(market, asset, speculate);
		}else if(market.getMarketName() == Market.DIGITAL_MARKET){
			return new DigitalBackTest(market, asset, speculate);
		}
		
		return null;
	}

}
