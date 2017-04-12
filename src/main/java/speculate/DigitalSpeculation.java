package speculate;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import asset.Asset;
import asset.AssetFactory;
import backtest.BackTest;
import backtest.BackTestFactory;
import entry.Entry;
import market.Market;
import position.Position;
import utils.DateUtils;
import vault.Vault;

public class DigitalSpeculation implements Speculate {
	
	BigDecimal accountEquity;
	BigDecimal totalReturnPercent = new BigDecimal(0.00);
	
	List<Entry> entryList = new ArrayList<>();
	List<Entry> sortedEntryList = new ArrayList<>();
	
	List<Position> positionList = new ArrayList<>();
	List<Position> sortedPositionList = new ArrayList<>();
	
	List<Entry> unitList = new ArrayList<>();
	
	public DigitalSpeculation(Market market) {
		this.accountEquity = Speculate.DIGITAL_EQUITY;
	}

	@Override
	public void setAccountEquity(BigDecimal tradeResult) {
		this.accountEquity = this.accountEquity.add(tradeResult);
	}

	@Override
	public BigDecimal getAccountEquity() {
		return this.accountEquity;
	}
	
	@Override
	public void setTotalReturnPercent() {
		this.totalReturnPercent = this.accountEquity.subtract(Speculate.DIGITAL_EQUITY, MathContext.DECIMAL32)
				.divide(Speculate.DIGITAL_EQUITY, MathContext.DECIMAL32)
				.setScale(2, RoundingMode.HALF_DOWN)
				.multiply(new BigDecimal(100.00), MathContext.DECIMAL32);		
	}

	@Override
	public BigDecimal getTotalReturnPercent() {
		return this.totalReturnPercent;
	}
	
	@Override
	public void getAllOpenPositions(Vault vault) {
		AssetFactory assetFactory = new AssetFactory();
		BackTestFactory backTestFactory = new BackTestFactory();
		Asset asset;
		BackTest backtest;
		for(int i=0; i < vault.market.getAssets().size();i++){
			asset = assetFactory.createAsset(vault.market, vault.market.getAssets().get(i).toString());
			backtest = backTestFactory.newBackTest(vault.market, asset, vault.speculate);
			if(backtest.getLastPosition() != null && backtest.getLastPosition().isOpen()){
				vault.resultsList.add(backtest.getLastEntry().toString());
				vault.resultsList.add(backtest.getLastPosition().toString());
			}
		}
	}
	
	@Override
	public void getPositionsToClose(Vault vault) {
		AssetFactory assetFactory = new AssetFactory();
		BackTestFactory backTestFactory = new BackTestFactory();
		Asset asset; 
		BackTest backtest;
		for(int i=0; i < vault.market.getAssets().size();i++){
			asset = assetFactory.createAsset(vault.market, vault.market.getAssets().get(i).toString());
			backtest = backTestFactory.newBackTest(vault.market, asset, vault.speculate);
			if(backtest.getLastPosition() != null && backtest.getLastPosition().isOpen() == false){
				vault.resultsList.add(backtest.getLastEntry().toString());
				vault.resultsList.add(backtest.getLastPosition().toString());
			}
		}
	}
	
	@Override
	public void runBackTest(Vault vault) {
		AssetFactory assetFactory = new AssetFactory();
		BackTestFactory backTestFactory = new BackTestFactory();
		Asset asset; 
		BackTest backtest;
		for(int i=0; i < vault.market.getAssets().size();i++){
			asset = assetFactory.createAsset(vault.market, vault.market.getAssets().get(i).toString());
			backtest = backTestFactory.newBackTest(vault.market, asset, vault.speculate);
			if(backtest.getEntryList().size() > 0){
				for(int x = 0; x < backtest.getEntryList().size();x++){
					vault.speculate.setEntryList(backtest.getEntryList().get(x));
					vault.speculate.setPositionList(backtest.getPositionList().get(x));
				}
			}
		}
		
		//set sorted lists by date
		vault.speculate.setSortedEntryList(vault.speculate.getEntryList());
		vault.speculate.setSortedPositionList(vault.speculate.getPositionList());
		
		//get start date and number of days since
		Date startDate = vault.speculate.getSortedEntryList().get(0).getDateTime();
		int days = DateUtils.getNumberOfDaysSinceDate(startDate);
		
		vault.resultsList.add("***** START BACKTEST: " + DateUtils.dateToSimpleDateFormat(startDate));
		
		for(int z = 0; z < days; z++){
			Date date = DateUtils.addDays(startDate, z);
			vault.resultsList.add("DATE: " + DateUtils.dateToSimpleDateFormat(date));
			for(int k = 0; k < vault.speculate.getSortedEntryList().size();k++){
				Date entryDate = vault.speculate.getSortedEntryList().get(k).getDateTime();
				if(entryDate.equals(date) && this.unitList.size() < MAX_UNITS){
					vault.resultsList.add(vault.speculate.getSortedEntryList().get(k).toString());
					vault.speculate.addUnit(vault.speculate.getSortedEntryList().get(k));
				}
			}
			
			for(int t = 0; t < vault.speculate.getSortedPositionList().size();t++){
				Date closeDate = vault.speculate.getSortedPositionList().get(t).getDateTime();
				if(closeDate.equals(date)){
					for(int q = 0; q < this.unitList.size(); q++){
						if(vault.speculate.getSortedPositionList().get(t).getEntry().equals(this.unitList.get(q))){
							vault.resultsList.add(vault.speculate.getSortedPositionList().get(t).toString());
							vault.speculate.subtractUnit(vault.speculate.getSortedPositionList().get(t));
							vault.speculate.setAccountEquity(vault.speculate.getSortedPositionList().get(t).getProfitLossAmount());
							vault.speculate.setTotalReturnPercent();
							vault.resultsList.add(vault.speculate.toString());
						}
					}
				}
			}
		}
		
		vault.resultsList.add(" ***** END BACKTEST ***** ");
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
	public void setPositionList(Position position) {
		this.positionList.add(position);
		
	}

	@Override
	public List<Position> getPositionList() {
		return this.positionList;
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
	public String toString(){
		return "[ACCOUNT] " + "Balance: $" + this.accountEquity.setScale(2, RoundingMode.HALF_DOWN) + " Total Return: " + this.getTotalReturnPercent() + "% " + " Units: " + this.unitList.size();
	}
	
	

}
