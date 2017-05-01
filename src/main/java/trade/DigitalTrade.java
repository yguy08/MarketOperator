package trade;

import java.util.ArrayList;
import java.util.List;

import asset.Asset;
import entry.Entry;
import entry.EntryFactory;
import market.Market;
import speculator.Speculator;

public class DigitalTrade implements Trade {
	
	private List<Entry> newEntryList = new ArrayList<>();
	
	public DigitalTrade(){
		
	}

	@Override
	public void setNewEntries(Market market, Speculator speculator) {
		EntryFactory eFactory = new EntryFactory();
		Entry entry;
		for(Asset asset : market.getAssetList()){
			for(int x = speculator.getEntrySignalDays(); x < asset.getPriceList().size();x++){
				asset.setPriceSubList(x - speculator.getEntrySignalDays(),x+1);
				entry = eFactory.findEntry(market, asset, speculator);
				if(entry.isEntry()){
					newEntryList.add(entry);
				}
			}
		}
	}

	@Override
	public List<Entry> getNewEntries() {
		return newEntryList;
	}

}
