package tradeold;

import asset.Asset;
import market.Market;

public class TradeFactory {
	
	public Trade startTrading(String type, Market market, Asset asset){
		if(type == null){
			return null;
		}
		if(type.equalsIgnoreCase(Trade.BACK_TEST)){
			return new BackTest(market, asset);
		}else if(type.equalsIgnoreCase(Trade.LIVE)){
			return new Live(market,asset);
		}
		
		return null;
	}

}
