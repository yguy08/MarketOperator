package speculate;

import java.math.BigDecimal;

import asset.Asset;
import asset.AssetFactory;
import entry.DigitalEntry;
import entry.Entry;
import market.Market;

public class DigitalSpeculation implements Speculate {
	
	BigDecimal accountEquity;
	Market market;
	Asset asset;
	
	public DigitalSpeculation(Market market, Asset asset) {
		this.market = market;
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
		return "[ACCOUNT]" + this.accountEquity;
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

}
