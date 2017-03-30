package entry;

import asset.Asset;
import market.Market;

public class EntryFactory {
	
	public Entry findEntry(Market market, Asset asset){
		if(market == null){
			return null;
		}
		
		if(market.getMarketName().equals(Market.STOCK_MARKET)){
			return new StockEntry(market, asset);
		}else if(market.getMarketName().equals(Market.DIGITAL_MARKET)){
			return new DigitalEntry(market, asset);
		}
		
		return null;
	}

}
