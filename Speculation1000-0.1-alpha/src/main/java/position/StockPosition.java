package position;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import asset.Asset;
import asset.StockChartData;
import entry.Entry;
import market.Market;
import speculate.Speculate;
import utils.DateUtils;

public class StockPosition implements Position {
	
	Market market;
	Asset asset;
	Entry entry;
	
	String Date;
	BigDecimal currentPrice;
	BigDecimal maxPrice;
	BigDecimal minPrice;
	
	int locationIndex;
	
	BigDecimal profitLossPercent = new BigDecimal(0.00);
	BigDecimal profitLossAmount = new BigDecimal(0.00);
	
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

	@Override
	public void setExit() {
		if(this.currentPrice.compareTo(this.minPrice) == 0 && this.entry.getDirection() == Speculate.LONG){
			this.open = false;
			setProfitLossPercent();
			setProfitLossAmount(this.entry);
		}else if(this.currentPrice.compareTo(this.maxPrice) == 0 && this.entry.getDirection() == Speculate.SHORT){
			this.open = false;
			setProfitLossPercent();
			setProfitLossAmount(this.entry);
		}else{
			this.open = true;
			setProfitLossPercent();
			setProfitLossAmount(this.entry);
		}
	}

	@Override
	public Boolean isOpen() {
		return this.open;
	}
	
	@Override
	public void setProfitLossPercent(){
		BigDecimal calcPL = this.currentPrice.subtract(this.entry.getCurrentPrice())
				.divide(this.entry.getCurrentPrice(), MathContext.DECIMAL32);
		if(this.entry.getDirection() == Speculate.LONG){
			this.profitLossPercent = calcPL;
		}else{
			//negate for shorts since lower price would be a win, higher a loss
			this.profitLossPercent = calcPL.negate();
		}
	}
	
	@Override
	public BigDecimal getProfitLossPercent() {
		return this.profitLossPercent;
	}
	
	@Override
	public void setProfitLossAmount(Entry entry) {
		this.profitLossAmount = entry.getOrderTotal().multiply(this.profitLossPercent, MathContext.DECIMAL32);
	}

	@Override
	public BigDecimal getProfitLossAmount() {
		return this.profitLossAmount;
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
	public String getDate() {
		return this.Date;
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
		return this.locationIndex;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("[POSITION]");
		sb.append(" $" + this.asset.getAsset());
		sb.append(" Open:" + this.open);
		sb.append(" Entry [Date: " + this.entry.getDate());
		sb.append(" Price: " + this.entry.getCurrentPrice() + "]");
		
		if(this.isOpen()){
			sb.append(" Current [Date: " + this.getDate());
			sb.append(" Price: " + this.currentPrice + "]");
		}else{
			sb.append(" Close [Date: " + this.getDate());
			sb.append(" Price: " + this.currentPrice + "]");
		}
		sb.append(" P/L [%: " + this.profitLossPercent.multiply(new BigDecimal(100.00), MathContext.DECIMAL32).setScale(2, RoundingMode.HALF_DOWN) + "%");
		sb.append(" Amount: $" + this.profitLossAmount.setScale(2, RoundingMode.HALF_DOWN) + "]");
		return sb.toString();
	}
	
	@Override
	public Date getDateTime() {
		String date = this.getDate();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date dateTime;
		try {
			dateTime = df.parse(date);
			return DateUtils.localDateToUTCDate(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;		
	}

	@Override
	public Entry getEntry() {
		return this.entry;
	}

	@Override
	public void setAssetName(String assetName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMaxPrice(BigDecimal maxPrice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMinPrice(BigDecimal minPrice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BigDecimal getMaxPrice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getMinPrice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOpen(boolean isOpen) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getAssetName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Position copy(Position position, Entry entry) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCurrentPrice(BigDecimal currentPrice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BigDecimal getCurrentPrice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProfitLossPercent(Position position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEntryDate(String date) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getEntryDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEntryPrice(BigDecimal entryPrice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BigDecimal getEntryPrice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDate(String date) {
		// TODO Auto-generated method stub
		
	}

}