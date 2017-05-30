package entry;

import asset.Asset;
import market.Market;
import speculator.Speculator;

public class EntryFactory {

	public Entry findEntry(Market market, Asset asset, Speculator speculator) {
		// TODO Auto-generated method stub
		return new DigitalEntry(market, asset, speculator);
	}
	
}
