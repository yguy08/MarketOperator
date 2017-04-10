package speculate;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import asset.Asset;
import asset.AssetFactory;
import backtest.BackTest;
import backtest.BackTestFactory;
import entry.Entry;
import javafx.collections.ObservableList;
import market.Market;
import position.Position;

public class StockSpeculation implements Speculate {

	BigDecimal accountEquity;
	BigDecimal totalReturnPercent = new BigDecimal(0.00);
	
	List<Entry> entryList = new ArrayList<>();
	List<Entry> sortedEntryList = new ArrayList<>();
	
	List<Position> positionList = new ArrayList<>();
	List<Position> sortedPositionList = new ArrayList<>();
	
	BigDecimal maxUnits = new BigDecimal(4.00);
	
	public StockSpeculation(Market market) {
		this.accountEquity = Speculate.STOCK_EQUITY;
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
		this.totalReturnPercent = this.accountEquity.subtract(Speculate.STOCK_EQUITY, MathContext.DECIMAL32)
				.divide(Speculate.STOCK_EQUITY, MathContext.DECIMAL32)
				.setScale(2, RoundingMode.HALF_DOWN)
				.multiply(new BigDecimal(100.00), MathContext.DECIMAL32); 
	}

	@Override
	public BigDecimal getTotalReturnPercent() {
		return this.totalReturnPercent;
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
	public void getLastEntrySingleMarket(Market market) {
		SpeculateFactory speculateFactory = new SpeculateFactory();
		Speculate speculator = speculateFactory.startSpeculating(market);
		for(int i=0; i < market.getAssets().size();i++){
			AssetFactory assetFactory = new AssetFactory();
			Asset asset = assetFactory.createAsset(market, market.getAssets().get(i).toString());
			BackTestFactory backTestFactory = new BackTestFactory();
			BackTest backtest = backTestFactory.newBackTest(market, asset, speculator);
			System.out.println(backtest.getLastEntry().toString());
		}		
	}
	
	@Override
	public void getLastPositionSingleMarket(Market market) {
		SpeculateFactory speculateFactory = new SpeculateFactory();
		Speculate speculator = speculateFactory.startSpeculating(market);
		for(int i=0; i < market.getAssets().size();i++){
			AssetFactory assetFactory = new AssetFactory();
			Asset asset = assetFactory.createAsset(market, market.getAssets().get(i).toString());
			BackTestFactory backTestFactory = new BackTestFactory();
			BackTest backtest = backTestFactory.newBackTest(market, asset, speculator);
			System.out.println(backtest.getLastPosition().toString());
		}		
	}
	
	@Override
	public void getCurrentEntriesSingleMarket(Market market) {
		SpeculateFactory speculateFactory = new SpeculateFactory();
		Speculate speculator = speculateFactory.startSpeculating(market);
		for(int i=0; i < market.getAssets().size();i++){
			AssetFactory assetFactory = new AssetFactory();
			Asset asset = assetFactory.createAsset(market, market.getAssets().get(i).toString());
			BackTestFactory backTestFactory = new BackTestFactory();
			BackTest backtest = backTestFactory.newBackTest(market, asset, speculator);
			
			if(backtest.getLastEntry().getLocationIndex() == backtest.getAsset().getPriceList().size() - 1){
				System.out.println(backtest.getLastEntry().toString());
			}
		}		
	}

	@Override
	public void getAllOpenPositionsSingleMarket(Market market) {
		SpeculateFactory speculateFactory = new SpeculateFactory();
		Speculate speculator = speculateFactory.startSpeculating(market);
		for(int i=0; i < market.getAssets().size();i++){
			AssetFactory assetFactory = new AssetFactory();
			Asset asset = assetFactory.createAsset(market, market.getAssets().get(i).toString());
			BackTestFactory backTestFactory = new BackTestFactory();
			BackTest backtest = backTestFactory.newBackTest(market, asset, speculator);
			if(backtest.getLastPosition().isOpen()){
			System.out.println(backtest.getLastPosition().toString());
			}
		}
	}
	
	@Override
	public void getPositionsToCloseSingleMarket(Market market) {
		SpeculateFactory speculateFactory = new SpeculateFactory();
		Speculate speculator = speculateFactory.startSpeculating(market);
		for(int i=0; i < market.getAssets().size();i++){
			AssetFactory assetFactory = new AssetFactory();
			Asset asset = assetFactory.createAsset(market, market.getAssets().get(i).toString());
			BackTestFactory backTestFactory = new BackTestFactory();
			BackTest backtest = backTestFactory.newBackTest(market, asset, speculator);
			if(backtest.getLastPosition().isOpen() == false){
				System.out.println(backtest.getLastPosition().toString());
			}
		}
	}

	@Override
	public void backTestSingleAsset(Market market, Asset asset) {
		SpeculateFactory speculateFactory = new SpeculateFactory();
		Speculate speculator = speculateFactory.startSpeculating(market);
		BackTestFactory backTestFactory = new BackTestFactory();
		BackTest backtest = backTestFactory.newBackTest(market, asset, speculator);
		for(int i = 0; i < backtest.getEntryList().size();i++){
			speculator.setAccountEquity(backtest.getPositionList().get(i).getProfitLossAmount());
			speculator.setTotalReturnPercent();
			System.out.println(backtest.getEntryList().get(i).toString());
			System.out.println(backtest.getPositionList().get(i).toString());
			System.out.println(speculator.toString());
		}
	}
	
	@Override
	public void backTestAllAssetsSingleMarket(Market market) {
		SpeculateFactory speculateFactory = new SpeculateFactory();
		Speculate speculate = speculateFactory.startSpeculating(market);
		for(int i=0; i < market.getAssets().size();i++){
				AssetFactory assetFactory = new AssetFactory();
				Asset asset = assetFactory.createAsset(market, market.getAssets().get(i).toString());
				BackTestFactory backTestFactory = new BackTestFactory();
				BackTest backtest = backTestFactory.newBackTest(market, asset, speculate);
				for(int x = 0; x < backtest.getEntryList().size();x++){
					speculate.setAccountEquity(backtest.getPositionList().get(x).getProfitLossAmount());
					speculate.setTotalReturnPercent();
					System.out.println(backtest.getEntryList().get(x).toString());
					System.out.println(backtest.getPositionList().get(x).toString());
					System.out.println(speculate.toString());
				}
			}
	}
	
	@Override
	public String toString(){
		return "[ACCOUNT] " + "Balance: " + this.accountEquity + " Total Return (%): " + this.getTotalReturnPercent();
	}

	@Override
	public void getAllOpenPositionsWithEntry(Market market, ObservableList<String> obsList) {
		SpeculateFactory speculateFactory = new SpeculateFactory();
		Speculate speculator = speculateFactory.startSpeculating(market);
		for(int i=0; i < market.getAssets().size();i++){
			AssetFactory assetFactory = new AssetFactory();
			Asset asset = assetFactory.createAsset(market, market.getAssets().get(i).toString());
			BackTestFactory backTestFactory = new BackTestFactory();
			BackTest backtest = backTestFactory.newBackTest(market, asset, speculator);
			if(backtest.getLastPosition().isOpen()){
				obsList.add(backtest.getLastEntry().toString());
				obsList.add(backtest.getLastPosition().toString());
			}
		}
		
	}

	@Override
	public void getPositionsToCloseSingleMarket(Market market, ObservableList<String> obsList) {
		SpeculateFactory speculateFactory = new SpeculateFactory();
		Speculate speculator = speculateFactory.startSpeculating(market);
		for(int i=0; i < market.getAssets().size();i++){
			AssetFactory assetFactory = new AssetFactory();
			Asset asset = assetFactory.createAsset(market, market.getAssets().get(i).toString());
			BackTestFactory backTestFactory = new BackTestFactory();
			BackTest backtest = backTestFactory.newBackTest(market, asset, speculator);
			if(backtest.getLastPosition().isOpen() == false){
				obsList.add(backtest.getLastEntry().toString());
				obsList.add(backtest.getLastPosition().toString());
			}
		}
		
	}

	@Override
	public void backTest(Market market, ObservableList<String> obsList) {
		SpeculateFactory speculateFactory = new SpeculateFactory();
		Speculate speculate = speculateFactory.startSpeculating(market);
		for(int i=0; i < market.getAssets().size();i++){
				AssetFactory assetFactory = new AssetFactory();
				Asset asset = assetFactory.createAsset(market, market.getAssets().get(i).toString());
				BackTestFactory backTestFactory = new BackTestFactory();
				BackTest backtest = backTestFactory.newBackTest(market, asset, speculate);
				//only checking longs because shorts can blow up easily..
				obsList.add(backtest.getAsset().getAsset().toString());
				obsList.add(speculate.getAccountEquity().toPlainString());
				for(int x = 0; x < backtest.getEntryList().size();x++){
					if(backtest.getEntryList().get(x).getDirection() != Speculate.SHORT){
					speculate.setAccountEquity(backtest.getPositionList().get(x).getProfitLossAmount());
					speculate.setTotalReturnPercent();
					obsList.add(backtest.getEntryList().get(x).toString());
					obsList.add(backtest.getPositionList().get(x).toString());
					obsList.add(speculate.toString());
					}
				}
		}
		
	}
	
	@Override
	public void newBackTest(Market market, Speculate speculate, ObservableList<String> obsList) {
		AssetFactory assetFactory = new AssetFactory();
		BackTestFactory backTestFactory = new BackTestFactory();
		Asset asset; 
		BackTest backtest;
		for(int i=0; i < market.getAssets().size();i++){
			asset = assetFactory.createAsset(market, market.getAssets().get(i).toString());
			backtest = backTestFactory.newBackTest(market, asset, speculate);
			for(int x = 0; x < backtest.getEntryList().size();x++){
				setEntryList(backtest.getEntryList().get(x));
			}
			
			for(int y = 0; y < backtest.getPositionList().size();y++){
				setPositionList(backtest.getPositionList().get(y));
			}
		}
		
		setSortedEntryList(this.entryList);
		setSortedPositionList(this.positionList);
				
		for(int p = 0; p < this.getSortedEntryList().size();p++){
			Date start = this.getSortedEntryList().get(p).getDateTime();
			obsList.add(start.toString());
			obsList.add(this.getSortedEntryList().get(p).toString());
			
			for(int t = 0; t < this.getSortedPositionList().size();t++){
				if(this.getSortedPositionList().get(t).getDateTime().compareTo(start) == 0){
					obsList.add(this.getSortedPositionList().get(t).toString());
				}
			}
		}
	
	}

	

}
