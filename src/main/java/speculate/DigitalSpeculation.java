package speculate;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import asset.Asset;
import asset.AssetFactory;
import backtest.BackTest;
import backtest.BackTestFactory;
import entry.DigitalEntry;
import entry.Entry;
import entry.EntryFactory;
import market.Market;

public class DigitalSpeculation implements Speculate {
	
	BigDecimal accountEquity;
	BigDecimal totalReturnPercent;
	
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
		this.totalReturnPercent = this.accountEquity.subtract(Speculate.STOCK_EQUITY, MathContext.DECIMAL32)
				.divide(Speculate.STOCK_EQUITY, MathContext.DECIMAL32)
				.setScale(2, RoundingMode.HALF_DOWN);		
	}

	@Override
	public BigDecimal getTotalReturnPercent() {
		return this.totalReturnPercent;
	}

	@Override
	public void getAllEntriesSingleMarket(Market market) {
		AssetFactory assetFactory = new AssetFactory();
		EntryFactory entryFactory = new EntryFactory();
		for(int i = 0; i < market.getAssets().size();i++){
			Asset asset = assetFactory.createAsset(market, market.getAssets().get(i).toString());
			asset.setPriceSubList(asset.getPriceList().size() - Speculate.ENTRY, asset.getPriceList().size());
			Entry entry = entryFactory.findEntry(market, asset);
			if(entry.isEntry()){
				System.out.println(entry.toString());
			}
		}		
	}

	@Override
	public void backTestAllAssetsSingleMarket(Market market) {
		AssetFactory assetFactory = new AssetFactory();
		SpeculateFactory speculateFactory = new SpeculateFactory();
		Speculate speculate = speculateFactory.startSpeculating(market);
		for(int i=0; i < market.getAssets().size();i++){
				Asset asset = assetFactory.createAsset(market, market.getAssets().get(i).toString());
				BackTestFactory backTestFactory = new BackTestFactory();
				BackTest backtest = backTestFactory.newBackTest(market, asset, speculate);
				System.out.println(asset.getAsset() + " " + speculate.toString());
			}
	}

	@Override
	public void backTestSingleAsset(Market market, Asset asset) {
		SpeculateFactory speculateFactory = new SpeculateFactory();
		Speculate speculate = speculateFactory.startSpeculating(market);
		BackTestFactory backTestFactory = new BackTestFactory();
		BackTest backtest = backTestFactory.newBackTest(market, asset, speculate);
		System.out.println(asset.getAsset() + " " + speculate.toString());
	}

	@Override
	public void getAllEntriesAllMarkets() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toString(){
		return "[ACCOUNT] " + "Balance: " + this.accountEquity + " Total Return (%): " + this.getTotalReturnPercent();
	}

}
