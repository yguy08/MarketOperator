package position;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import asset.Asset;
import asset.StockChartData;
import entry.Entry;
import exit.Exit;
import market.Market;
import speculate.Speculate;

public class StockPosition implements Position {
	
	Market market;
	Asset asset;
	Entry entry;
	
	String Date;
	BigDecimal currentPrice;
	BigDecimal maxPrice;
	BigDecimal minPrice;
	int locationIndex;
	
	BigDecimal profitLoss;
	
	BigDecimal maxUnitSize;
	
	BigDecimal stop;
	
	Boolean open;
	
	List<StockChartData> priceSubList = new ArrayList<>();
	List<BigDecimal> subListClose = new ArrayList<>();
	
	public StockPosition(Market market, Asset asset, Entry entry){
		this.market = market;
		this.asset = asset;
		this.entry = entry;
		this.open = true;
		setPriceSubList(this.asset);
		setDate();
		setCurrentPrice();
		setMaxPrice();
		setMinPrice();
		setLocationAsIndex();
		setExit();
	}

	@Override
	public void setExit() {
		if(this.currentPrice.compareTo(this.minPrice) == 0 && this.entry.getDirection() == Entry.LONG){
			this.open = false;
			setProfitLoss();
		}else if(this.currentPrice.compareTo(this.maxPrice) == 0 && this.entry.getDirection() == Entry.SHORT){
			this.open = false;
			setProfitLoss();
		}else{
			this.open = true;
		}
	}

	@Override
	public Boolean isOpen() {
		return this.open;
	}
	
	public void setProfitLoss(){
		BigDecimal calcPL = this.currentPrice.subtract(this.entry.getCurrentPrice());
		calcPL = calcPL.divide(this.entry.getCurrentPrice(), MathContext.DECIMAL32);
		if(this.entry.getDirection() == Entry.LONG){
			this.profitLoss = calcPL.multiply(new BigDecimal(100.00));
		}else{
			//update for shorts...
			this.profitLoss = calcPL.multiply(new BigDecimal(100.00));
		}
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("[POSITION]");
		sb.append(" Open: " + this.entry.getCurrentPrice());
		sb.append(" Closed: " + this.currentPrice);
		sb.append(" P/L: " + this.profitLoss);
		sb.append(" Close index: " + this.locationIndex);
		return sb.toString();
	}

	@Override
	public void setCustomUnitSize() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public BigDecimal getCustomUnitSize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPriceSubList(Asset asset) {
		this.priceSubList = (List<StockChartData>) asset.getPriceSubList();		
	}

	@Override
	public void setDate() {
		this.Date = this.priceSubList.get(this.priceSubList.size() - 1).getDate();		
	}

	@Override
	public void setCurrentPrice() {
		this.currentPrice = this.priceSubList.get(this.priceSubList.size() - 1).getClose();		
	}

	@Override
	public void setMaxPrice() {
		List<BigDecimal> maxList = new ArrayList<>();
		for(int x = 0; x < this.priceSubList.size(); x++){
			maxList.add(this.priceSubList.get(x).getClose());
		}
		
		this.maxPrice = Collections.max(maxList);		
	}

	@Override
	public void setMinPrice() {
		List<BigDecimal> minList = new ArrayList<>();
		for(int x = 0; x < this.priceSubList.size(); x++){
			minList.add(this.priceSubList.get(x).getClose());
		}
		
		this.minPrice = Collections.min(minList);
		
	}

	@Override
	public void setLocationAsIndex() {
		this.locationIndex = this.asset.getPriceList().indexOf(this.priceSubList.get(this.priceSubList.size() - 1));
		
	}

	@Override
	public int getLocationIndex() {
		// TODO Auto-generated method stub
		return this.locationIndex;
	}

}
