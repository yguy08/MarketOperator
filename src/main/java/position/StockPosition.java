package position;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import asset.Asset;
import asset.StockChartData;
import entry.Entry;
import market.Market;
import speculate.Speculate;

public class StockPosition implements Position {
	
	Market market;
	Asset asset;
	Entry entry;
	Speculate speculator;
	
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
	
	public StockPosition(Market market, Asset asset, Entry entry){
		this.market = market;
		this.asset = asset;
		this.entry = entry;
		this.open = true;
		setPriceSubList();
		setDate();
		setCurrentPrice();
		setMaxPrice();
		setMinPrice();
		setLocationAsIndex();
		setExit();
	}
	
	public StockPosition(Market market, Asset asset, Entry entry, Speculate speculator){
		this.market = market;
		this.asset = asset;
		this.entry = entry;
		this.speculator = speculator;
		this.open = true;
		setPriceSubList();
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
			updateAccountBalance();
		}else if(this.currentPrice.compareTo(this.maxPrice) == 0 && this.entry.getDirection() == Entry.SHORT){
			this.open = false;
			setProfitLoss();
			updateAccountBalance();
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
			//negate for shorts since lower price would be a win, higher a loss
			calcPL = calcPL.negate();
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
		sb.append("Account bal: " + this.speculator.getAccountEquity());
		return sb.toString();
	}

	@Override
	public void setPriceSubList() {
		this.priceSubList = (List<StockChartData>) this.asset.getPriceSubList();		
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

	@Override
	public void updateAccountBalance() {
		BigDecimal close = this.currentPrice.multiply(this.entry.getUnitSize(), MathContext.DECIMAL32);
		this.speculator.setAccountEquity(close);
	}

}
