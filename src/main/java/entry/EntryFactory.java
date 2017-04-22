package entry;

import asset.Asset;
import market.Market;
import speculator.Speculator;

public class EntryFactory {
	
	public Entry findEntry(Market market, Asset asset, Speculator speculator){
		switch(market.getMarketName()){
		case Market.DIGITAL_MARKET:
			return new DigitalEntry(market, asset, speculator);
		default:
			return new DigitalEntry(market, asset, speculator);
		}
	}
}
