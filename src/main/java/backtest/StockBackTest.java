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
	
	Market market;
	Asset asset;
	Speculate speculator;
	
	EntryFactory entryFactory = new EntryFactory();
	Entry entry;
	List<Entry> entryList = new ArrayList<>();
	
	PositionFactory positionFactory = new PositionFactory();
	Position position;
	List<Position> positionList = new ArrayList<>();
	
	public StockBackTest(Market market, Asset asset, Speculate speculator){
		this.market = market;
		this.asset = asset;
		this.speculator = speculator;
		runBackTest();
	}

	@Override
	public void runBackTest() {
		for(int x = Speculate.ENTRY; x < this.asset.getPriceList().size();x++){
			this.asset.setPriceSubList(x - Speculate.ENTRY, x + 1);
			entry = entryFactory.findEntry(this.market, this.asset, this.speculator);
			if(entry.isEntry()){
				setEntryList(entry);
				for(int y = this.entry.getLocationIndex();y<this.asset.getPriceList().size() || position.isOpen() == false; y++, x++){
					this.asset.setPriceSubList(y - Speculate.EXIT, y + 1);
					this.position = positionFactory.createPosition(this.market, this.asset, this.entry);
					if(position.isOpen() == false){
						setPositionList(position);
						break;
					//for if a position is still open
					}else if(position.isOpen() && x == this.asset.getPriceList().size() - 1){
						setPositionList(position);
						break;
					}
				}
			}
		}		
	}

	@Override
	public void setEntryList(Entry entry) {
		this.entryList.add(entry);
		
	}

	@Override
	public List<Entry> getEntryList() {
		return this.entryList;
	}

	@Override
	public void setPositionList(Position position) {
		this.positionList.add(position);
		
	}

	@Override
	public List<Position> getPositionList() {
		return this.positionList;
	}

	@Override
	public Entry getLastEntry() {
		return this.entryList.get(this.entryList.size()-1);
	}

	@Override
	public Position getLastPosition() {
		return this.positionList.get(this.positionList.size()-1);
	}

	@Override
	public Asset getAsset() {
		return this.asset;
	}

}
