package speculate;

import java.math.BigDecimal;
import java.math.MathContext;

import asset.Asset;
import asset.AssetFactory;
import backtest.BackTest;
import backtest.BackTestFactory;
import entry.DigitalEntry;
import entry.Entry;
import market.Market;

public class DigitalSpeculation implements Speculate {
	
	BigDecimal accountEquity;
	String marketName;
	Market market;
	Asset asset;
	
	public DigitalSpeculation(Market market, Asset asset) {
		this.market = market;
		this.marketName = asset.getAsset().toString();
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
	public String toString(){
		return "[ACCOUNT]" + this.marketName + " " + this.accountEquity + " TOTAL: " + this.accountEquity.subtract(Speculate.DIGITAL_EQUITY)
		.divide(Speculate.DIGITAL_EQUITY, MathContext.DECIMAL32)
		.multiply(new BigDecimal(100.00), MathContext.DECIMAL32);
	}

	@Override
	public void getAllEntries() {
		for(int i = 0; i < this.market.getAssets().size();i++){
			AssetFactory assetFactory = new AssetFactory();
			this.asset = assetFactory.createAsset(this.market, this.market.getAssets().get(i).toString());
			this.asset.setPriceSubList(this.asset.getPriceList().size() - Speculate.ENTRY, this.asset.getPriceList().size());
			Entry digitalEntry = new DigitalEntry(this.market, this.asset);
			if(digitalEntry.isEntry()){
				System.out.println(digitalEntry.toString());
			}
		}
	}

	@Override
	public void runBackTestOnAllMarkets(Market market) {
		AssetFactory assetFactory = new AssetFactory();
		for(int i=0; i < market.getAssets().size();i++){
				Asset asset = assetFactory.createAsset(market, market.getAssets().get(i).toString());
				SpeculateFactory speculateFactory = new SpeculateFactory();
				Speculate speculate = speculateFactory.startSpeculating(market, asset);
				BackTestFactory backTestFactory = new BackTestFactory();
				BackTest backtest = backTestFactory.newBackTest(market, asset, speculate);
			}
	}
	
	
	
	
	}
