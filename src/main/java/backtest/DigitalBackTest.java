package backtest;

import java.math.BigDecimal;
import java.math.MathContext;
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
import speculate.Speculate;
import speculate.SpeculateFactory;
import utils.DateUtils;

public class DigitalBackTest implements BackTest {
	
	Market market;
	Asset asset;
	Speculate speculator;
	
	EntryFactory entryFactory = new EntryFactory();
	Entry entry;
	List<Entry> entryList = new ArrayList<>();
	Entry updatedSizeEntry;
	
	List<Entry> sortedEntryList = new ArrayList<>();
	
	PositionFactory positionFactory = new PositionFactory();
	Position position;
	List<Position> positionList = new ArrayList<>();
	Position updatedSizePosition;

	List<Position> sortedPositionList = new ArrayList<>();
	
	Speculate updatedSizeSpeculator;
	
	List<Entry> unitList = new ArrayList<>();
	
	List<String> resultsList = new ArrayList<>();
	
	String startDate;
	
	public DigitalBackTest(Market market, Asset asset, Speculate speculator){
		this.market = market;
		this.asset = asset;
		this.speculator = speculator;
	}
	
	public DigitalBackTest(Market market, Speculate speculator){
		this.market = market;
		this.speculator = speculator;
	}

	@Override
	public void runBackTest() {
		for(int x = Speculate.ENTRY; x < this.asset.getPriceList().size();x++){
			this.asset.setPriceSubList(x - Speculate.ENTRY, x + 1);
			entry = entryFactory.findEntry(this.market, this.asset, this.speculator);
			if(entry.isEntry() && entry.getDirection() == Speculate.LONG){
				setEntryList(entry);
				for(int y = this.entry.getLocationIndex(); y < this.asset.getPriceList().size() || position.isOpen() == false; y++, x++){
					this.asset.setPriceSubList(y - Speculate.EXIT, y + 1);
					this.position = positionFactory.createPosition(this.market, this.asset, this.entry);
					if(position.isOpen() == false){
						setPositionList(position);
						break;
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
		
		SpeculateFactory speculateFactory = new SpeculateFactory();
		Speculate updateSpeculator = speculateFactory.startSpeculating(market);
		
		//get start date and number of days since
		Date startDate = this.getSortedEntryList().get(0).getDateTime();
		this.setStartDate(startDate);
		int days = DateUtils.getNumberOfDaysSinceDate(startDate);
		
		this.resultsList.add(this.getStartBackTestToString());
		
		for(int z = 0; z <= days; z++){
			Date currentDateOfBacktest = DateUtils.addDays(startDate, z);
			this.resultsList.add("DATE: " + DateUtils.dateToSimpleDateFormat(currentDateOfBacktest));
			for(int k = 0; k < this.getSortedEntryList().size();k++){
				this.entry = this.getSortedEntryList().get(k);
				this.updatedSizeEntry = this.entry.copy(this.entry, this.speculator);
				Date entryDate = this.entry.getDateTime();
				boolean isUnitSpotOpen = (this.unitList.size() < Speculate.MAX_UNITS);
				boolean isEntryOnCurrentDayOfBacktest = entryDate.equals(currentDateOfBacktest);
				
				if(isEntryOnCurrentDayOfBacktest && isUnitSpotOpen){
					this.resultsList.add(this.updatedSizeEntry.toString());
					this.addUnit(this.entry);
				}
			}
			
			for(int t = 0; t < this.getSortedPositionList().size();t++){
				Date closeDate = this.getSortedPositionList().get(t).getDateTime();
				boolean isCloseOnCurrentDayOfBacktest = closeDate.equals(currentDateOfBacktest);
				boolean isClosePastCurrentDayOfBacktest = DateUtils.getNumberOfDaysFromDate(currentDateOfBacktest, closeDate) > 7;
				boolean isPositionToCheck = isClosePastCurrentDayOfBacktest ? false : isCloseOnCurrentDayOfBacktest;
				if(isPositionToCheck){
					for(int q = 0; q < this.unitList.size(); q++){
						boolean isPositionInUnitList = this.getSortedPositionList().get(t).getEntry().equals(this.unitList.get(q));
						if(isPositionInUnitList){
							position = this.getSortedPositionList().get(t);
							updatedSizePosition = position.copy(position, updatedSizeEntry);
							this.resultsList.add(updatedSizePosition.toString());
							this.subtractUnit(position);
							updateSpeculator.setAccountEquity(updatedSizePosition.getProfitLossAmount());
							updateSpeculator.setTotalReturnPercent();
							this.resultsList.add(updateSpeculator.toString() + this.unitList.size());
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
		return (this.entryList.size() > 0) ? this.entryList.get(this.entryList.size() - 1) : null;
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
		StringBuilder sb = new StringBuilder();
		sb.append("***** START BACKTEST: ");
		sb.append(" Date: " + this.getStartDate());
		sb.append(" Balance: " + Speculate.DIGITAL_EQUITY);
		sb.append(" Entry: " + Speculate.ENTRY);
		sb.append(" Exit: " + Speculate.EXIT);
		sb.append(" Units: " + Speculate.MAX_UNITS);
		sb.append(" Risk: " + Speculate.RISK.multiply(new BigDecimal(100.00), MathContext.DECIMAL32).setScale(1) + "%");
		sb.append(" Stop: " + Speculate.STOP);
		return sb.toString();
	}

	@Override
	public void setStartDate(Date date) {
		this.startDate = DateUtils.dateToSimpleDateFormat(date);
	}

	@Override
	public String getStartDate() {
		return this.startDate;
	}

	@Override
	public void dataSetUp() {
		AssetFactory assetFactory = new AssetFactory();
		for(int f = 0; f < this.market.getAssets().size(); f++){
			this.asset = assetFactory.createAsset(this.market, this.market.getAssets().get(f).toString());
			for(int x = Speculate.ENTRY; x < this.asset.getPriceList().size();x++){
				this.asset.setPriceSubList(x - Speculate.ENTRY, x + 1);
				this.entry = entryFactory.findEntry(this.market, this.asset, this.speculator);
				boolean isFilteredEntry = Speculate.LONG_FILTER ? (entry.isEntry() && entry.isLong()) : entry.isEntry();
				if(isFilteredEntry){
					setEntryList(entry);
					for(int y = this.entry.getLocationIndex(); y < this.asset.getPriceList().size() || position.isOpen() == false; y++, x++){
						this.asset.setPriceSubList(y - Speculate.EXIT, y + 1);
						this.position = positionFactory.createPosition(this.market, this.asset, this.entry);
						boolean isLastPosition = (this.asset.getPriceList().size() - 1 == x);
						boolean isPositionRecordable = position.isOpen() == false ? true : isLastPosition;
						if(isPositionRecordable){
							setPositionList(position);
							System.out.println("Closed or Last Position" + this.position.toString());
							break;
						}else{
							System.out.println("Still open and not last position: " + this.position.toString());
						}
					}
				}
			}
		}
			
			setSortedEntryList(this.entryList);
			setSortedPositionList(this.positionList);
	}

	@Override
	public void setEntryList(List<Entry> entryList) {
		this.entryList = entryList;
	}

	@Override
	public void setPositionList(List<Position> positionList) {
		this.positionList = positionList;
	}

}
