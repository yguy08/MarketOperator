package position;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import asset.Asset;
import entry.Entry;
import exit.Exit;
import market.Market;
import speculate.Speculate;

public class StockPosition implements Position {
	
	Market market;
	Asset asset;
	Entry entry;
	
	BigDecimal trueRange;
	
	public StockPosition(Market market, Asset asset, Entry entry){
		this.market = market;
		this.asset = asset;
		this.entry = entry;
		setTrueRange();
	}

	@Override
	public void setTrueRange() {
		
		if(this.asset.getCloseList().size() < Speculate.ENTRY){
			//skip?
		}
		
		//set first TR for 0 position (H-L)
		BigDecimal tR = ((this.asset.getHighList().get(0)).subtract(this.asset.getCloseList().get(0)).abs());
		for(int x = 1; x < Speculate.ENTRY; x++){
			List<BigDecimal> trList = Arrays.asList(
				this.asset.getHighList().get(x).subtract(this.asset.getLowList().get(x).abs(), MathContext.DECIMAL32),
				this.asset.getHighList().get(x).subtract(this.asset.getCloseList().get(x-1).abs(), MathContext.DECIMAL32),
				this.asset.getCloseList().get(x-1).subtract(this.asset.getLowList().get(x).abs(), MathContext.DECIMAL32)					
			);
				
			tR = tR.add(Collections.max(trList));
		}
		
		tR = tR.divide(new BigDecimal(Speculate.ENTRY), MathContext.DECIMAL32);
		
		//20 exponential moving average
		for(int x = Speculate.ENTRY; x < this.entry.getLocationIndex();x++){
			List<BigDecimal> trList = Arrays.asList(
					this.asset.getHighList().get(x).subtract(this.asset.getLowList().get(x).abs(), MathContext.DECIMAL32),
					this.asset.getHighList().get(x).subtract(this.asset.getCloseList().get(x-1).abs(), MathContext.DECIMAL32),
					this.asset.getCloseList().get(x-1).subtract(this.asset.getLowList().get(x).abs(), MathContext.DECIMAL32)
			);
			
			tR = tR.multiply(new BigDecimal(Speculate.ENTRY - 1), MathContext.DECIMAL32)
					.add((Collections.max(trList)), MathContext.DECIMAL32).
					divide(new BigDecimal(Speculate.ENTRY), MathContext.DECIMAL32);
		}
		
		this.trueRange = tR;
	}

	@Override
	public BigDecimal getTrueRange() {
		return this.trueRange;
	}

	@Override
	public void setPositionSize() {
		// TODO Auto-generated method stub

	}

	@Override
	public BigDecimal getPositionSize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStop() {
		// TODO Auto-generated method stub

	}

	@Override
	public BigDecimal getStop() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setExit() {
		// TODO Auto-generated method stub

	}

	@Override
	public Exit getExit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isOpen() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void open() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("ATR: " + this.trueRange);
		return sb.toString();
	}

}
