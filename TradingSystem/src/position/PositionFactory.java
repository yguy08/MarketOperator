package position;

import asset.Asset;
import entry.Entry;
import market.Market;

public class PositionFactory {
	
	public Position createPosition(Market market, Asset asset, Entry entry){
		if(market == null){
			return null;
		}
		
		if(market.getMarketName().equalsIgnoreCase(Market.STOCK_MARKET)){
			return new StockPosition(market, asset, entry);
		}else if(market.getMarketName().equalsIgnoreCase(Market.DIGITAL_MARKET)){
			return new DigitalPosition(market, asset, entry);
		}
		
		return null;
	}

}
