package backtest;

import java.util.ArrayList;
import java.util.List;

import asset.Asset;
import entry.Entry;
import entry.EntryFactory;
import market.Market;
import position.Position;
import position.PositionFactory;
import speculate.Speculate;

public class StockBackTest implements BackTest {
	
	String tradeStyle;
	Market market;
	Asset asset;
	Speculate speculator;
	
	EntryFactory entryFactory = new EntryFactory();
	Entry entry;
	
	PositionFactory positionFactory = new PositionFactory();
	Position position;
	
	List<Entry> entryList = new ArrayList<>();
	
	public StockBackTest(Market market, Asset asset, Speculate speculate){
		this.tradeStyle = Speculate.BACK_TEST;
		this.market = market;
		this.asset = asset;
		this.speculator = speculate;
		runBackTest();
	}

	@Override
	public void runBackTest() {
		for(int x = Speculate.ENTRY; x < this.asset.getPriceList().size();x++){
			this.asset.setPriceSubList(x - Speculate.ENTRY, x + 1);
			entry = entryFactory.findEntry(this.market, this.asset, this.speculator);
			if(entry.isEntry()){
				entryList.add(entry);
				System.out.println(entry.toString());
				for(int y = this.entry.getLocationIndex();y<this.asset.getPriceList().size() || position.isOpen() == false; y++, x++){
					this.asset.setPriceSubList(y - Speculate.EXIT, y + 1);
					this.position = positionFactory.createPosition(this.market, this.asset, this.entry, this.speculator);
					if(position.isOpen() == false){
						System.out.println(position.toString());
						break;
					}
				}
			}
		}
		
	}

}
