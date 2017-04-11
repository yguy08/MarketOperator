package speculate;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import asset.Asset;
import asset.AssetFactory;
import backtest.BackTest;
import backtest.BackTestFactory;
import entry.Entry;
import javafx.collections.ObservableList;
import market.Market;
import position.Position;
import vault.Vault;

public class DigitalSpeculation implements Speculate {
	
	BigDecimal accountEquity;
	BigDecimal totalReturnPercent = new BigDecimal(0.00);
	
	
	List<Entry> entryList = new ArrayList<>();
	List<Entry> sortedEntryList = new ArrayList<>();
	
	List<Position> positionList = new ArrayList<>();
	List<Position> sortedPositionList = new ArrayList<>();
	
	List<Entry> buyEntryList = new ArrayList<>();
	List<Position> buyPositionList = new ArrayList<>();
	
	int maxUnits = 4;
	int unit = 0;
	
	Market market;
	
	public DigitalSpeculation(Market market) {
		this.accountEquity = Speculate.DIGITAL_EQUITY;
		this.market = market;
	}
	
	public Market getMarket(){
		return this.market;
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
			if(backtest.getLastPosition().isOpen()){
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
			if(backtest.getLastPosition().isOpen() == false){
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
			for(int x = 0; x < backtest.getEntryList().size();x++){
				vault.speculate.setEntryList(backtest.getEntryList().get(x));
				vault.speculate.setPositionList(backtest.getPositionList().get(x));
			}			
		}
		
		//set sorted lists by date
		vault.speculate.setSortedEntryList(vault.speculate.getEntryList());
		vault.speculate.setSortedPositionList(vault.speculate.getPositionList());
		
		for(int i = 0; i < vault.speculate.getSortedEntryList().size();i++){
			Date date = vault.speculate.getSortedEntryList().get(i).getDateTime();
			while(vault.speculate.getSortedEntryList().get(i).getDateTime().equals(date)){
				vault.resultsList.add(vault.speculate.getSortedEntryList().get(i).toString());
				i++;
			}
			
			for(int z = 0; z < vault.speculate.getSortedPositionList().size();z++){
				if(vault.speculate.getSortedPositionList().get(z).getDateTime().equals(date)){
					vault.resultsList.add(vault.speculate.getSortedPositionList().get(z).toString());
					vault.speculate.setAccountEquity(vault.speculate.getSortedPositionList().get(z).getProfitLossAmount());
					vault.speculate.setTotalReturnPercent();
					vault.resultsList.add(vault.speculate.toString());
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
	public void addUnit(){
		++this.unit;
	}
	
	@Override
	public void subtractUnit(){
		--this.unit;
	}
	
	public int getUnit(){
		return this.unit;
	}
	
	@Override
	public String toString(){
		return "[ACCOUNT] " + "Balance: " + this.accountEquity + " Total Return (%): " + this.getTotalReturnPercent();
	}
	
	

}
