package trade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import asset.Asset;
import asset.AssetFactory;
import entry.Entry;
import entry.EntryFactory;
import market.Market;
import position.Position;
import position.PositionFactory;
import speculator.Speculator;
import util.DateUtils;

public class StockBackTest implements BackTest {
	
	Market market;
	Asset asset;
	Speculator speculator;
	
	EntryFactory entryFactory = new EntryFactory();
	Entry entry;
	List<Entry> entryList = new ArrayList<>();
	List<Entry> sortedEntryList = new ArrayList<>();
	
	PositionFactory positionFactory = new PositionFactory();
	Position position;
	List<Position> positionList = new ArrayList<>();
	List<Position> sortedPositionList = new ArrayList<>();
	
	List<Entry> unitList = new ArrayList<>();
	
	List<String> resultsList = new ArrayList<>();
	
	public StockBackTest(Market market, Asset asset, Speculator speculator){
		this.market = market;
		this.asset = asset;
		this.speculator = speculator;
	}
	
	public StockBackTest(Market market, Speculator speculator){
		this.market = market;
		this.speculator = speculator;
	}

	@Override
	public void runBackTest() {
		for(int x = Speculator.ENTRY; x < this.asset.getPriceList().size();x++){
			this.asset.setPriceSubList(x - Speculator.ENTRY, x + 1);
			entry = entryFactory.findEntry(this.market, this.asset, this.speculator);
			if(entry.isEntry()){
				setEntryList(entry);
				for(int y = this.entry.getLocationIndex(); y < this.asset.getPriceList().size() || position.isOpen() == false; y++, x++){
					this.asset.setPriceSubList(y - Speculator.EXIT, y + 1);
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
	public void protoBackTest() {
	AssetFactory assetFactory = new AssetFactory();
	for(int f = 0; f < this.market.getAssets().size(); f++){
		this.asset = assetFactory.createAsset(this.market, this.market.getAssets().get(f).toString());
		for(int x = Speculator.ENTRY; x < this.asset.getPriceList().size();x++){
			this.asset.setPriceSubList(x - Speculator.ENTRY, x + 1);
			entry = entryFactory.findEntry(this.market, this.asset, this.speculator);
			if(entry.isEntry()){
				setEntryList(entry);
				for(int y = this.entry.getLocationIndex(); y < this.asset.getPriceList().size() || position.isOpen() == false; y++, x++){
					this.asset.setPriceSubList(y - Speculator.EXIT, y + 1);
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
		
		setSortedEntryList(this.entryList);
		setSortedPositionList(this.positionList);
		
		//get start date and number of days since
		Date startDate = this.getSortedEntryList().get(0).getDateTime();
		int days = DateUtils.getNumberOfDaysSinceDate(startDate);
		
		this.resultsList.add("***** START BACKTEST: " + DateUtils.dateToSimpleDateFormat(startDate));
		
		for(int z = 0; z <= days; z++){
			Date date = DateUtils.addDays(startDate, z);
			this.resultsList.add("DATE: " + DateUtils.dateToSimpleDateFormat(date));
			for(int k = 0; k < this.getSortedEntryList().size();k++){
				entry = this.getSortedEntryList().get(k);
				entry.setUnitSize(speculator);
				entry.setOrderTotal();
				Date entryDate = this.getSortedEntryList().get(k).getDateTime();
				if(entryDate.equals(date) && this.unitList.size() < Speculator.MAX_UNITS){
					this.resultsList.add(entry.toString() + this.unitList.size());
					this.addUnit(entry);
				}
			}
			
			for(int t = 0; t < this.getSortedPositionList().size();t++){
				Date closeDate = this.getSortedPositionList().get(t).getDateTime();
				if(closeDate.equals(date)){
					for(int q = 0; q < this.unitList.size(); q++){
						if(this.getSortedPositionList().get(t).getEntry().equals(this.unitList.get(q))){
							position = this.getSortedPositionList().get(t);
							position.setProfitLossAmount(this.unitList.get(q));
							this.resultsList.add(position.toString());
							this.subtractUnit(this.getSortedPositionList().get(t));
							speculator.setAccountEquity(position.getProfitLossAmount());
							speculator.setTotalReturnPercent();
							this.resultsList.add(speculator.toString() + this.unitList.size());
						}
					}
				}
			}
		}
		
		this.resultsList.add(" ***** END BACKTEST ***** ");
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
		if(this.entryList.size() > 0){
			return this.entryList.get(this.entryList.size() - 1);
		}else{
			return null;
		}
	}

	@Override
	public Position getLastPosition() {
		if(this.positionList.size() > 0){
			return this.positionList.get(this.positionList.size() - 1);
		}else{
			return null;
		}
	}
	
	@Override
	public void setSortedEntryList(List<Entry> entryList) {
		Collections.sort(entryList, new Comparator<Entry>() {
		    public int compare(Entry o1, Entry o2) {
		        return o1.getDateTime().compareTo(o2.getDateTime());
		    }
		});
		
		this.sortedEntryList = entryList;
	}

	@Override
	public List<Entry> getSortedEntryList() {
		return this.sortedEntryList;
	}
	
	@Override
	public void setSortedPositionList(List<Position> positionList) {
		Collections.sort(positionList, new Comparator<Position>() {
		    public int compare(Position o1, Position o2) {
		        return o1.getDateTime().compareTo(o2.getDateTime());
		    }
		});
		
		this.sortedPositionList = positionList;
		
	}

	@Override
	public List<Position> getSortedPositionList() {
		return this.sortedPositionList;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(" [BACKTEST] ");
		return null;
	}

	@Override
	public void addUnit(Entry entry) {
		this.unitList.add(entry);
	}

	@Override
	public void subtractUnit(Position position) {
		int e = this.unitList.indexOf(position.getEntry());
		this.unitList.remove(e);
	}

	@Override
	public List<String> getResultsList() {
		return this.resultsList;
	}

	@Override
	public String getStartBackTestToString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStartDate(Date date) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getStartDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dataSetUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEntryList(List<Entry> entryList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPositionList(List<Position> positionList) {
		// TODO Auto-generated method stub
		
	}

}
