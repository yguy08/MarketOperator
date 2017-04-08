package speculate;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import asset.Asset;
import asset.AssetFactory;
import backtest.BackTest;
import backtest.BackTestFactory;
import javafx.collections.ObservableList;
import market.Market;

public class DigitalSpeculation implements Speculate {
	
	BigDecimal accountEquity;
	BigDecimal totalReturnPercent = new BigDecimal(0.00) ;
	
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
}
