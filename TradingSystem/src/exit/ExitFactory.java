package exit;

import asset.Asset;
import entry.Entry;
import market.Market;
import position.Position;

public class ExitFactory {
	
	public Exit setClose(Market market, Asset asset, Entry entry, Position position){
		if(market == null){
			return null;
		}
		
		if(market.getMarketName().equals(Market.STOCK_MARKET)){
			return new StockExit(market, asset, entry, position);
		}else if(market.getMarketName().equals(Market.DIGITAL_MARKET)){
			return new DigitalExit(market, asset, entry, position);
		}
		
		return null;
	}

}
