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
import entry.Entry;
import market.Market;
import speculate.Speculate;
import utils.DateUtils;
import utils.StringFormatter;
import asset.PoloniexOfflineChartData;

public class PoloniexOfflinePosition implements Position {
	
	Market market;
	Asset asset;
	Entry entry;
	
	String Date, entryDate;
	BigDecimal currentPrice, maxPrice, minPrice, entryPrice;	
	int locationIndex;
	
	BigDecimal profitLossPercent = new BigDecimal(0.00);
	BigDecimal profitLossAmount = new BigDecimal(0.00);
	
	Boolean open, isLong;
	
	String assetName;
	
	List<PoloniexOfflineChartData> priceSubList = new ArrayList<>();
	
	public PoloniexOfflinePosition(Market market, Asset asset, Entry entry){
		this.market = market;
		this.asset = asset;
		this.entry = entry;
		this.open = true;
		this.isLong = this.entry.getDirection() == Speculate.LONG;
		this.assetName = this.entry.getAssetName();
		this.entryPrice = this.entry.getCurrentPrice();
		setPriceSubList();
		setEntryDate(this.entry.getDate());
		setDate();
		setCurrentPrice();
		setMaxPrice();
		setMinPrice();
		setLocationAsIndex();
		setExit();
	}
	
	public PoloniexOfflinePosition(Position position, Entry entry){
		setAssetName(position.getAssetName());
		setOpen(position.isOpen());
		setMaxPrice(position.getMaxPrice());
		setMinPrice(position.getMinPrice());
		setDate(position.getDate());
		setEntryPrice(position.getEntryPrice());
		setEntryDate(position.getEntryDate());
		setCurrentPrice(position.getCurrentPrice());
		setProfitLossPercent(position);
		setProfitLossAmount(entry);
	}
	
	@Override
	public Position copy(Position position, Entry entry) {
		Position digitalPosition = new PoloniexOfflinePosition(position, entry);
		return digitalPosition;
	}

	@Override
	public void setExit() {
		
		boolean isEqualToHigh = this.currentPrice.compareTo(this.maxPrice) == 0;
		boolean isEqualToLow = this.currentPrice.compareTo(this.minPrice) == 0;
		boolean isLongLessThanStop = this.currentPrice.compareTo(this.entry.getStop()) < 0;
		boolean isShortLessThanStop = this.currentPrice.compareTo(this.entry.getStop()) > 0;
		
		boolean isLessThanStop = isLong ? isLongLessThanStop : isShortLessThanStop;
		boolean isBreakOutDown = isLong ? isEqualToLow : isEqualToHigh;
		
		boolean isClose = (isBreakOutDown || isLessThanStop);
		
		this.open = isClose ? false : true;
		
		setProfitLossPercent();
		setProfitLossAmount(this.entry);
	}

	@Override
	public Boolean isOpen() {
		return this.open;
	}
	
	@Override
	public void setProfitLossPercent(){
		BigDecimal calcPL = this.currentPrice.subtract(this.entry.getCurrentPrice())
				.divide(this.entry.getCurrentPrice(), MathContext.DECIMAL32);
		this.profitLossPercent = (isLong) ? calcPL : calcPL.negate();
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
		this.priceSubList = (List<PoloniexOfflineChartData>) this.asset.getPriceSubList();		
	}

	@Override
	public void setDate() {
		this.Date = DateUtils.dateToSimpleDateFormat(this.priceSubList.get(this.priceSubList.size() - 1).getDate());
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
		sb.append(" " + this.getAssetName());
		sb.append(" Open:" + this.open);
		sb.append(" Entry [Date:" + this.getEntryDate());
		sb.append(" Price: " + StringFormatter.bigDecimalToEightString(this.getEntryPrice()) + "]");
		if(this.isOpen()){
			sb.append(" Current [Date: " + this.getDate());
			sb.append(" Price: " + StringFormatter.bigDecimalToEightString(this.currentPrice) + "]");
		}else{
			sb.append(" Close [Date: " + this.getDate());
			sb.append(" Price: " + StringFormatter.bigDecimalToEightString(this.currentPrice) + "]");
		}
		sb.append(" P/L [%: " + this.profitLossPercent.multiply(new BigDecimal(100.00), MathContext.DECIMAL32).setScale(2, RoundingMode.HALF_DOWN) + "%");
		sb.append(" Amount: " + this.profitLossAmount.setScale(4, RoundingMode.HALF_DOWN) + "]");
		sb.append(" High: [" + StringFormatter.bigDecimalToEightString(this.maxPrice) + "]");
		sb.append(" Low: [" + StringFormatter.bigDecimalToEightString(this.minPrice) + "]" );
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
	public Entry getEntry(){
		return this.entry;
	}

	@Override
	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}
	
	public void setProfitLossPercent(Position position){
		this.profitLossPercent = position.getProfitLossPercent();
	}

	@Override
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	@Override
	public void setEntryDate(String date) {
		this.entryDate = date;
	}

	@Override
	public String getEntryDate() {
		return this.entryDate;
	}

	@Override
	public void setEntryPrice(BigDecimal entryPrice) {
		this.entryPrice = entryPrice;
	}

	@Override
	public BigDecimal getEntryPrice() {
		return this.entryPrice;
	}

	@Override
	public void setDate(String date) {
		this.Date = date;
	}

	@Override
	public void setMaxPrice(BigDecimal maxPrice) {
		this.maxPrice = maxPrice;
	}

	@Override
	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}

	@Override
	public BigDecimal getMaxPrice() {
		return this.maxPrice;
	}

	@Override
	public BigDecimal getMinPrice() {
		return this.minPrice;
	}

	@Override
	public void setOpen(boolean isOpen) {
		this.open = isOpen;
	}

	@Override
	public String getAssetName() {
		return this.assetName;
	}

	@Override
	public BigDecimal getCurrentPrice() {
		return this.currentPrice;
	}
}
