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
			if(market.getAssets().get(i).toString().endsWith("BTC")){
				AssetFactory assetFactory = new AssetFactory();
				Asset asset = assetFactory.createAsset(market, market.getAssets().get(i).toString());
				BackTestFactory backTestFactory = new BackTestFactory();
				BackTest backtest = backTestFactory.newBackTest(market, asset, speculator);
				System.out.println(backtest.getLastPosition().toString());
			}
		}		
	}
	
	@Override
	//something wrong with this one...
	public void getCurrentEntriesSingleMarket(Market market) {
		SpeculateFactory speculateFactory = new SpeculateFactory();
		Speculate speculator = speculateFactory.startSpeculating(market);
		for(int i=0; i < market.getAssets().size();i++){
			AssetFactory assetFactory = new AssetFactory();
			Asset asset = assetFactory.createAsset(market, market.getAssets().get(i).toString());
			BackTestFactory backTestFactory = new BackTestFactory();
			BackTest backtest = backTestFactory.newBackTest(market, asset, speculator);
			
			if(backtest.getLastEntry().getLocationIndex() == backtest.getAsset().getPriceList().size() - 1 && backtest.getLastEntry()!=null){
				System.out.println(backtest.getLastEntry().toString());
			}else{
				System.out.println("nope!");
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
			if(market.getAssets().get(i).toString().endsWith("BTC")){
				AssetFactory assetFactory = new AssetFactory();
				Asset asset = assetFactory.createAsset(market, market.getAssets().get(i).toString());
				BackTestFactory backTestFactory = new BackTestFactory();
				BackTest backtest = backTestFactory.newBackTest(market, asset, speculate);
				//only checking longs because shorts can blow up easily..
				for(int x = 0; x < backtest.getEntryList().size();x++){
					speculate.setAccountEquity(backtest.getPositionList().get(x).getProfitLossAmount());
					speculate.setTotalReturnPercent();
					System.out.println(backtest.getEntryList().get(x).toString());
					System.out.println(backtest.getPositionList().get(x).toString());
					System.out.println(speculate.toString());
				}
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
			if(market.getAssets().get(i).toString().endsWith("BTC")){
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
	public void newBackTest(Market market, Speculate speculate, ObservableList<String> obsList) {
		for(int p = 1; p < speculate.getSortedEntryList().size();p++){
			Calendar d1 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
			d1.setTime(speculate.getSortedEntryList().get(p).getDateTime());
			Calendar d2 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
			d2.setTime(speculate.getSortedEntryList().get(p-1).getDateTime());
			int z = d1.compareTo(d2);
			if(d1.compareTo(d2) == 0){
				if(speculate.getUnit() < 4){
					obsList.add(speculate.getSortedEntryList().get(p).toString());
					speculate.addUnit();
					speculate.setBuyEntryList(speculate.getSortedEntryList().get(p));
				}else{
					
				}
				continue;
			}else if(d1.compareTo(d2) > 0){
				if(speculate.getUnit() < 4){
					obsList.add(speculate.getSortedEntryList().get(p).toString());
					speculate.addUnit();
					speculate.setBuyEntryList(speculate.getSortedEntryList().get(p));
				}else{
					
				}
				
				for(int x = 0; x < speculate.getSortedPositionList().size();x++){
					Calendar d3 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
					d3.setTime(speculate.getPositionList().get(x).getDateTime());
					if(d3.equals(d1)){
						obsList.add(speculate.getSortedPositionList().get(x).toString());
						speculate.subtractUnit();
						speculate.setAccountEquity(speculate.getSortedPositionList().get(x).getProfitLossAmount());
						speculate.setBuyPositionList(speculate.getSortedPositionList().get(x));
						obsList.add(speculate.toString());
					}
				}
			}
		}
	}
	
	@Override
	public Speculate newNewBackTest(Market market, Speculate speculate) {
		AssetFactory assetFactory = new AssetFactory();
		BackTestFactory backTestFactory = new BackTestFactory();
		Asset asset; 
		BackTest backtest;
		for(int i=0; i < market.getAssets().size();i++){
			asset = assetFactory.createAsset(market, market.getAssets().get(i).toString());
			backtest = backTestFactory.newBackTest(market, asset, speculate);
			for(int x = 0; x < backtest.getEntryList().size();x++){
				speculate.setEntryList(backtest.getEntryList().get(x));
			}
			
			for(int y = 0; y < backtest.getPositionList().size();y++){
				speculate.setPositionList(backtest.getPositionList().get(y));
			}
		}
		
		speculate.setSortedEntryList(this.entryList);
		speculate.setSortedPositionList(this.positionList);
		return speculate;
	}

	@Override
	public void setBuyEntryList(Entry entry) {
		// TODO Auto-generated method stub
		this.buyEntryList.add(entry);
	}

	@Override
	public List<Entry> getBuyEntryList() {
		// TODO Auto-generated method stub
		return this.buyEntryList;
	}

	@Override
	public void setBuyPositionList(Position position) {
		this.buyPositionList.add(position);
	}

	@Override
	public List<Entry> getBuyPositionList() {
		// TODO Auto-generated method stub
		return this.buyEntryList;
	}
	
	

}
