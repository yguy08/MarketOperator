package speculate;

import java.math.BigDecimal;

import asset.Asset;
import market.Market;

public class StockSpeculation implements Speculate {

	BigDecimal accountEquity;
	
	public StockSpeculation(Market market, Asset asset) {
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
	public String toString(){
		return "[ACCOUNT]" + this.accountEquity;
	}

	@Override
	public void getAllEntries() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void runBackTestOnAllMarkets(Market market) {
		// TODO Auto-generated method stub
		
	}
	
	

}
