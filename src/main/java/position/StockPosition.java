package position;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;
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
	BigDecimal dollarVol;
	BigDecimal unit;
	
	BigDecimal stop;
	
	public StockPosition(Market market, Asset asset, Entry entry){
		this.market = market;
		this.asset = asset;
		this.entry = entry;
		setTrueRange();
		setDollarVol();
		setUnitSize();
		setStop();
	}

	@Override
	public void setTrueRange() {
		//consider instance where list is too small...
		if(this.asset.getCloseList().size() < Speculate.MOVING_AVG){
			//skip?
		}
		
		//set first TR for 0 position (H-L)
		BigDecimal tR = ((this.asset.getHighList().get(0)).subtract(this.asset.getCloseList().get(0)).abs());
		for(int x = 1; x < Speculate.MOVING_AVG; x++){
			List<BigDecimal> trList = Arrays.asList(
				this.asset.getHighList().get(x).subtract(this.asset.getLowList().get(x).abs(), MathContext.DECIMAL32),
				this.asset.getHighList().get(x).subtract(this.asset.getCloseList().get(x-1).abs(), MathContext.DECIMAL32),
				this.asset.getCloseList().get(x-1).subtract(this.asset.getLowList().get(x).abs(), MathContext.DECIMAL32));
				
				tR = tR.add(Collections.max(trList));
		}
		
		tR = tR.divide(new BigDecimal(Speculate.MOVING_AVG), MathContext.DECIMAL32);
		
		//20 exponential moving average
		for(int x = Speculate.MOVING_AVG; x < this.entry.getLocationIndex();x++){
			List<BigDecimal> trList = Arrays.asList(
					this.asset.getHighList().get(x).subtract(this.asset.getLowList().get(x).abs(), MathContext.DECIMAL32),
					this.asset.getHighList().get(x).subtract(this.asset.getCloseList().get(x-1).abs(), MathContext.DECIMAL32),
					this.asset.getCloseList().get(x-1).subtract(this.asset.getLowList().get(x).abs(), MathContext.DECIMAL32));
					
					tR = tR.multiply(new BigDecimal(Speculate.MOVING_AVG - 1), MathContext.DECIMAL32)
					.add((Collections.max(trList)), MathContext.DECIMAL32).
					divide(new BigDecimal(Speculate.MOVING_AVG), MathContext.DECIMAL32);
		}
		
		this.trueRange = tR;
	}

	@Override
	public BigDecimal getTrueRange() {
		return this.trueRange;
	}
	
	@Override
	public void setDollarVol() {
		// Dollar Volatility = N (ATR) * price
		this.dollarVol = this.trueRange.multiply(entry.getCurrentPrice(), MathContext.DECIMAL32);
	}

	@Override
	public BigDecimal getDollarVol() {
		return this.dollarVol;
	}

	@Override
	public void setUnitSize() {
		// Position size = equity * risk / dollar vol
		this.unit = (Speculate.STOCK_EQUITY.multiply(Speculate.RISK, MathContext.DECIMAL32)).divide(this.dollarVol, MathContext.DECIMAL32);
	}

	@Override
	public BigDecimal getUnitSize() {
		return this.unit;
	}

	@Override
	public void setStop() {
		if(this.entry.getDirection().equals(Entry.LONG)){
			this.stop = this.entry.getCurrentPrice().subtract(Speculate.STOP.multiply(trueRange, MathContext.DECIMAL32));
		}else if(this.entry.getDirection().equals(Entry.SHORT)){
			this.stop = this.entry.getCurrentPrice().add(Speculate.STOP.multiply(trueRange, MathContext.DECIMAL32));
		}
		
	}

	@Override
	public BigDecimal getStop() {
		return this.stop;
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
		sb.append(" Dollar vol: " + this.dollarVol);
		sb.append(" Unit size: " + this.unit);
		sb.append(" Stop: " + this.stop);
		return sb.toString();
	}

}
