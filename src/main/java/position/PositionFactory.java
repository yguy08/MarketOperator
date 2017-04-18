package position;

import asset.Asset;
import entry.Entry;
import market.Market;

public class PositionFactory {
	
	public Position createPosition(Market market, Asset asset, Entry entry){
		Position position;
		switch(market.getMarketName()){
		case Market.DIGITAL_MARKET:
			position = new DigitalPosition(market, asset, entry);
			break;
		case Market.POLONIEX_OFFLINE:
			position = new PoloniexOfflinePosition(market, asset, entry);
			break;
		case Market.STOCK_MARKET:
			position = new StockPosition(market, asset, entry);
			break;
		default:
			position = null;
			break;
		}
		
		return position;
	}

}
