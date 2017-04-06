package speculate;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

import asset.Asset;
import asset.AssetFactory;
import backtest.BackTest;
import backtest.BackTestFactory;
import entry.DigitalEntry;
import entry.Entry;
import entry.EntryFactory;
import market.Market;

public class StockSpeculation implements Speculate {

	BigDecimal accountEquity;
	BigDecimal totalReturnPercent;
	
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
	public void getLatestEntriesSingleMarket(Market market) {
		//REMOVE SPECULATE WHEN REMOVED FROM BACKTEST
		SpeculateFactory speculateFactory = new SpeculateFactory();
		Speculate speculate = speculateFactory.startSpeculating(market);
		for(int i=0; i < market.getAssets().size();i++){
			AssetFactory assetFactory = new AssetFactory();
			Asset asset = assetFactory.createAsset(market, market.getAssets().get(i).toString());
			BackTestFactory backTestFactory = new BackTestFactory();
			BackTest backtest = backTestFactory.newBackTest(market, asset, speculate);
			System.out.println(backtest.getLastEntry().toString());
		}		
	}

	@Override
	public void getAllOpenPositionsSingleMarket(Market market) {
		//REMOVE SPECULATE WHEN REMOVED FROM BACKTEST
		SpeculateFactory speculateFactory = new SpeculateFactory();
		Speculate speculate = speculateFactory.startSpeculating(market);
		for(int i=0; i < market.getAssets().size();i++){
			AssetFactory assetFactory = new AssetFactory();
			Asset asset = assetFactory.createAsset(market, market.getAssets().get(i).toString());
			BackTestFactory backTestFactory = new BackTestFactory();
			BackTest backtest = backTestFactory.newBackTest(market, asset, speculate);
			System.out.println(backtest.getLastPosition().toString());
		}
	}

	@Override
	public void backTestSingleAsset(Market market, Asset asset) {
		SpeculateFactory speculateFactory = new SpeculateFactory();
		Speculate speculate = speculateFactory.startSpeculating(market);
		BackTestFactory backTestFactory = new BackTestFactory();
		BackTest backtest = backTestFactory.newBackTest(market, asset, speculate);
		for(int i = 0; i < backtest.getEntryList().size();i++){
			speculate.setAccountEquity(backtest.getPositionList().get(i).getProfitLossAmount());
			speculate.setTotalReturnPercent();
			System.out.println(backtest.getEntryList().get(i).toString());
			System.out.println(backtest.getPositionList().get(i).toString());
			System.out.println(speculate.toString());
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
	

}
