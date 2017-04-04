package position;

import asset.Asset;
import entry.Entry;
import market.Market;
import speculate.Speculate;

public class PositionFactory {
	
	public Position createPosition(Market market, Asset asset, Entry entry, Speculate speculator){
		if(market == null){
			return null;
		}
		
		if(market.getMarketName().equalsIgnoreCase(Market.STOCK_MARKET)){
			return new StockPosition(market, asset, entry, speculator);
		}else if(market.getMarketName().equalsIgnoreCase(Market.DIGITAL_MARKET)){
			return new DigitalPosition(market, asset, entry, speculator);
		}
		
		return null;
	}

}
