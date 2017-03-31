package exit;

import asset.Asset;
import entry.Entry;
import market.Market;
import position.Position;

public class StockExit implements Exit {
	
	Market market;
	Asset asset;
	Entry entry;
	Position position;
	
	public StockExit(Market market, Asset asset, Entry entry, Position position){
		this.market = market;
		this.asset = asset;
		this.entry = entry;
		this.position = position;
	}

}
