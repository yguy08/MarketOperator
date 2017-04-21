package entry;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Date;

import asset.Asset;
import asset.StockChartData;
import market.Market;
import speculator.Speculator;
import util.DateUtils;

public class StockEntry implements Entry {
	
	Market market;
	Asset asset;
	Speculator speculator;
	
	List<StockChartData> priceSubList = new ArrayList<>();
	
	String Date;
	BigDecimal currentPrice;
	BigDecimal maxPrice;
	BigDecimal minPrice;
	BigDecimal averageTrueRange;
	BigDecimal stop;
	BigDecimal unitSize;
	BigDecimal orderTotal;
	
	int locationIndex;
	String direction = null;
	Boolean isEntry = false;	
	
	public StockEntry(Market market, Asset asset, Speculator speculator){
		this.market = market;
		this.asset	= asset;
		this.speculator = speculator;
		setPriceSubList();
		setLocationAsIndex();
		setEntry();
		
		if(this.isEntry){
			setTrueRange();
			setStop();
			setUnitSize(speculator);
			setOrderTotal();
		}
	}
	
	@Override
	public void setEntry() {
		if(this.currentPrice.compareTo(this.maxPrice) == 0){
			this.isEntry = true;
			this.direction = Speculator.LONG;
		}else if(this.currentPrice.compareTo(this.minPrice) == 0){
			this.isEntry = true;
			this.direction = Speculator.SHORT;
		}else{
			this.isEntry = false;
		}
	}

	@Override
	public void setDate(String date) {
		this.Date = this.priceSubList.get(this.priceSubList.size() - 1).getDate();
	}

	@Override
	public String getDate() {
		return this.Date;
	}

	@Override
	public BigDecimal getCurrentPrice() {
		return this.currentPrice;
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
	public String getDirection() {
		return this.direction;
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
	public Boolean isEntry() {
		return this.isEntry;
	}

	@Override
	public void setPriceSubList() {
		this.priceSubList = (List<StockChartData>) this.asset.getPriceSubList();
	}
	
	//True Range of prices per share, measured in Dollars per Share..if True Range is 1.25 it means max daily variations is $1.25 per share
	@Override
	public void setTrueRange() {
		//consider instance where list is too small...
		if(this.asset.getCloseList().size() < Speculator.MOVING_AVG){
			//skip?
		}
		
		//set first TR for 0 position (H-L)
		BigDecimal tR = ((this.asset.getHighList().get(0)).subtract(this.asset.getCloseList().get(0)).abs());
		for(int x = 1; x < Speculator.MOVING_AVG; x++){
			List<BigDecimal> trList = Arrays.asList(
				this.asset.getHighList().get(x).subtract(this.asset.getLowList().get(x).abs(), MathContext.DECIMAL32),
				this.asset.getHighList().get(x).subtract(this.asset.getCloseList().get(x-1).abs(), MathContext.DECIMAL32),
				this.asset.getCloseList().get(x-1).subtract(this.asset.getLowList().get(x).abs(), MathContext.DECIMAL32));
				
				tR = tR.add(Collections.max(trList));
		}
		
		tR = tR.divide(new BigDecimal(Speculator.MOVING_AVG), MathContext.DECIMAL32);
		
		//20 exponential moving average
		for(int x = Speculator.MOVING_AVG; x < this.getLocationIndex();x++){
			List<BigDecimal> trList = Arrays.asList(
					this.asset.getHighList().get(x).subtract(this.asset.getLowList().get(x).abs(), MathContext.DECIMAL32),
					this.asset.getHighList().get(x).subtract(this.asset.getCloseList().get(x-1).abs(), MathContext.DECIMAL32),
					this.asset.getCloseList().get(x-1).subtract(this.asset.getLowList().get(x).abs(), MathContext.DECIMAL32));
					
					tR = tR.multiply(new BigDecimal(Speculator.MOVING_AVG - 1), MathContext.DECIMAL32)
					.add((Collections.max(trList)), MathContext.DECIMAL32).
					divide(new BigDecimal(Speculator.MOVING_AVG), MathContext.DECIMAL32);
		}
		
		this.averageTrueRange = tR;
	}

	@Override
	public BigDecimal getTrueRange() {
		return this.averageTrueRange;
	}

	@Override
	public void setStop() {
		if(this.getDirection().equals(Speculator.LONG)){
			this.stop = this.getCurrentPrice().subtract(Speculator.STOP.multiply(this.getTrueRange(), MathContext.DECIMAL32));
		}else if(this.getDirection().equals(Speculator.SHORT)){
			this.stop = this.getCurrentPrice().add(Speculator.STOP.multiply(this.getTrueRange(), MathContext.DECIMAL32));
		}
		
	}

	@Override
	public BigDecimal getStop() {
		return this.stop;
	}
	
	@Override
	public void setUnitSize(Speculator speculator) {
		BigDecimal max = speculator.getAccountEquity().divide(this.currentPrice, MathContext.DECIMAL32).setScale(0, RoundingMode.DOWN);
		BigDecimal size = speculator.getAccountEquity().multiply(Speculator.RISK, MathContext.DECIMAL32)
				.divide(this.averageTrueRange, MathContext.DECIMAL32).setScale(0, RoundingMode.DOWN);
		if(size.compareTo(max) > 0){
			this.unitSize = max;
		}else{
			this.unitSize = size;
		}
	}
	
	@Override
	public BigDecimal getUnitSize() {
		return this.unitSize;
	}
	
	@Override
	public void setOrderTotal() {
		this.orderTotal = this.unitSize.multiply(this.currentPrice, MathContext.DECIMAL32);
	}
	
	@Override
	public BigDecimal getOrderTotal() {
		return this.orderTotal;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("[ENTRY]");
		sb.append(" [$" + this.asset.getAsset() + "]");
		sb.append(" Date: " + this.Date);
		sb.append(" Price:" + this.currentPrice);
		sb.append(" Direction:" + this.direction);
		sb.append(" ATR: " + this.averageTrueRange);
		sb.append(" Unit Size: " + this.unitSize);
		sb.append(" Total: $" + this.orderTotal.setScale(2, RoundingMode.HALF_DOWN));
		sb.append(" Stop: " + this.stop.setScale(2, RoundingMode.HALF_DOWN));
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
	public void setMaxPrice(List<?> priceSubList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMinPrice(List<?> priceSubList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDirection(String direction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTrueRange(BigDecimal trueRange) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStop(BigDecimal stop) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCurrentPrice(BigDecimal currentPrice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BigDecimal getVolume() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isLong() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Entry copy(Entry entry, Speculator speculator) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAssetName(String assetName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getAssetName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setVolume(BigDecimal volume) {
		// TODO Auto-generated method stub
		
	}	

}
