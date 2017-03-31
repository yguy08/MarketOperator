package speculate;

import asset.Asset;
import market.Market;

public class SpeculateFactory {
	
	public Speculate startSpeculating(String tradeType, Market market, Asset asset){
		if(tradeType == null){
			return null;
		}
		if(tradeType.equalsIgnoreCase(Speculate.BACK_TEST)){
			return new BackTest(market, asset);
		}else if(tradeType.equalsIgnoreCase(Speculate.LIVE)){
			return new Live(market,asset);
		}
		
		return null;
	}

}
