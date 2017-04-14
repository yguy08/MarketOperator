package entry;

import asset.Asset;
import market.Market;
import speculate.Speculate;

public class EntryFactory {
	
	public Entry findEntry(Market market, Asset asset, Speculate speculator){
		if(market == null){
			return null;
		}
		
		if(market.getMarketName().equals(Market.STOCK_MARKET)){
			return new StockEntry(market, asset, speculator);
		}else if(market.getMarketName().equals(Market.DIGITAL_MARKET)){
			return new DigitalEntry(market, asset, speculator);
		}else if(market.getMarketName().equals(Market.POLONIEX_OFFLINE)){
			return new PoloniexOfflineEntry(market, asset, speculator);
		}
		
		return null;
	}

}
