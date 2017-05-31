package backtest;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import asset.Asset;
import entry.Entry;
import entry.EntryFactory;
import market.Market;
import position.Position;
import position.PositionFactory;
import speculator.Speculator;
import util.DateUtils;

public class DigitalBackTest implements BackTest {
	
	Market market;
	
	Asset asset;
	
	Speculator speculator;
	
	Entry entry;
	
	List<Entry> entryList = new ArrayList<>();
	
	Entry updatedSizeEntry;
	
	List<Entry> sortedEntryList = new ArrayList<>();
	
	PositionFactory positionFactory = new PositionFactory();
	
	Position position;
	
	List<Position> positionList = new ArrayList<>();
	
	Position updatedSizePosition;

	List<Position> sortedPositionList = new ArrayList<>();
	
	Speculator updatedSizeSpeculator;
	
	List<Entry> unitList = new ArrayList<>();
	
	List<String> resultsList = new ArrayList<>();
	
	String startDate;
	
	public DigitalBackTest(Market market, Speculator speculator){
		this.market = market;
		this.speculator = speculator;
	}
	
	@Override
	public void getEntriesAtOrAboveEntryFlag(Market market, Speculator speculator) {
		Entry entry;
		for(Asset asset : market.getAssetList()){
			System.out.println("Finding entries for: " + asset.getAssetName());
			for(int x = speculator.getEntrySignalDays();x < asset.getPriceList().size();x++){
				asset.setPriceSubList(x - speculator.getEntrySignalDays(), x + 1);
				entry = EntryFactory.findEntry(market, asset, speculator);
				if(entry.isEntry()){
					setEntryList(entry);
					System.out.println(entry.toString());
				}				
			}
			
			setSortedEntryList(entryList);
			setSortedPositionList(positionList);
		}
	}
	
	@Override
	public void runBackTest() {
		for(int x = Speculator.ENTRY; x < this.asset.getPriceList().size();x++){
			this.asset.setPriceSubList(x - Speculator.ENTRY, x + 1);
			entry = EntryFactory.findEntry(this.market, this.asset, this.speculator);
			if(entry.isEntry()){
				setEntryList(entry);
				for(int y = this.entry.getLocationIndex(); y < this.asset.getPriceList().size() || position.isOpen() == false; y++, x++){
					this.asset.setPriceSubList(y - Speculator.EXIT, y + 1);
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
		
		//speculator = DigitalSpeculator.createAverageRiskSpeculator();
		
		
		//get start date and number of days since
		Date startDate = this.getSortedEntryList().get(0).getDateTime();
		this.setStartDate(startDate);
		int days = DateUtils.getNumDaysFromDateToToday(startDate);
		
		this.resultsList.add(this.getStartBackTestToString());
		
		for(int z = 0; z <= days; z++){
			Date currentDateOfBacktest = DateUtils.addDays(startDate, z);
			this.resultsList.add("DATE: " + DateUtils.dateToSimpleDateFormat(currentDateOfBacktest));
			for(int k = 0; k < this.getSortedEntryList().size();k++){
				this.entry = this.getSortedEntryList().get(k);
				//this.updatedSizeEntry = this.entry.copy(this.entry, speculator);
				Date entryDate = this.entry.getDateTime();
				boolean isUnitSpotOpen = (this.unitList.size() < speculator.getMaxUnits());
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
							speculator.setAccountBalance(updatedSizePosition.getProfitLossAmount());
							speculator.setTotalReturnPercent();
							this.resultsList.add(speculator.toString());
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
		    @Override
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
		    @Override
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
		sb.append(" Balance: " + speculator.getAccountBalance());
		sb.append(" Entry: " + speculator.getEntrySignalDays());
		sb.append(" Exit: " + speculator.getSellSignalDays());
		sb.append(" Units: " + speculator.getMaxUnits());
		sb.append(" Risk: " + speculator.getRisk().multiply(new BigDecimal(100.00), MathContext.DECIMAL32).setScale(1) + "%");
		sb.append(" Stop: " + speculator.getStopLength());
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
		for(Asset asset : market.getAssetList()){
			for(int x = speculator.getEntrySignalDays(); x < asset.getPriceList().size();x++){
				asset.setPriceSubList(x - speculator.getEntrySignalDays(), x + 1);
				entry = EntryFactory.findEntry(market, asset, speculator);
				boolean isFilteredEntry = Speculator.LONG_FILTER ? (entry.isEntry() && entry.isLong()) : entry.isEntry();
				if(isFilteredEntry){
					setEntryList(entry);
					for(int y = this.entry.getLocationIndex(); y < asset.getPriceList().size() || position.isOpen() == false; y++, x++){
						asset.setPriceSubList(y - Speculator.EXIT, y + 1);
						this.position = positionFactory.createPosition(this.market, asset, this.entry);
						boolean isLastPosition = (asset.getPriceList().size() - 1 == x);
						boolean isPositionRecordable = position.isOpen() == false ? true : isLastPosition;
						if(isPositionRecordable){
							setPositionList(position);
							break;
						}else{

						}
					}
				}
			}
		}
		
		setSortedEntryList(entryList);
		setSortedPositionList(positionList);
	}

}
