package speculate;

import asset.Asset;
import market.Market;

public class SpeculateFactory {
	
	public Speculate startSpeculating(Market market, Asset asset){
		if(market == null){
			return null;
		}
		if(market.getMarketName().equalsIgnoreCase(Market.STOCK_MARKET)){
			return new StockSpeculation(market, asset);
		}
		
		return null;
	}

}
