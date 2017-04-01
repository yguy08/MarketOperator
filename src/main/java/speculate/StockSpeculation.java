package speculate;

import java.math.BigDecimal;

import asset.Asset;
import market.Market;

public class StockSpeculation implements Speculate {

	BigDecimal accountEquity;
	
	public StockSpeculation(Market market, Asset asset) {
		this.accountEquity = Speculate.DIGITAL_EQUITY;
	}

	@Override
	public void setAccountEquity(BigDecimal tradeResult) {
		this.accountEquity.add(tradeResult);
	}

	@Override
	public BigDecimal getAccountEquity() {
		return this.accountEquity;
	}
	
	@Override
	public String toString(){
		return "[ACCOUNT]" + this.accountEquity;
	}
	
	

}
