package entry;

import asset.Asset;
import market.Market;
import speculator.Speculator;

public class EntryFactory {

	public static Entry findEntry(Market market, Asset asset, Speculator speculator) {
		return new DigitalEntry(asset, speculator);
	}
	
}
