package speculate;

import java.util.ArrayList;
import java.util.List;

import asset.Asset;
import entry.Entry;
import entry.EntryFactory;
import market.Market;
import position.Position;
import position.PositionFactory;
import speculate.Speculate;

public class BackTest implements Speculate {
	
	String tradeStyle;
	Market market;
	Asset asset;
	
	EntryFactory entryFactory = new EntryFactory();
	Entry entry;
	
	PositionFactory positionFactory = new PositionFactory();
	Position position;
	
	List<Entry> entryList = new ArrayList<>();
	
	public BackTest(Market market, Asset asset){
		this.tradeStyle = Speculate.BACK_TEST;
		this.market = market;
		this.asset = asset;
		run();
	}
	
	public void run(){
		for(int x = Speculate.ENTRY; x < this.asset.getPriceList().size();x++){
			this.asset.setPriceSubList(this.asset.getPriceList().subList(x - Speculate.ENTRY, x + 1));
			entry = entryFactory.findEntry(this.market, this.asset);
			if(entry.isEntry()){
				entryList.add(entry);
				position = positionFactory.createPosition(this.market, this.asset, this.entry);
				System.out.println(entry.toString());
				System.out.println(position.toString());
			}
		}
	}

	@Override
	public List<Entry> getEntryList() {
		return this.entryList;
	}
	
	
}
