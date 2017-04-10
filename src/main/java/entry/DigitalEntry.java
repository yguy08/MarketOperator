package entry;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Date;

import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;

import asset.Asset;
import asset.StockChartData;
import market.Market;
import speculate.Speculate;

public class DigitalEntry implements Entry {
	
	Market market;
	Asset asset;
	Speculate speculator;
	
	List<PoloniexChartData> priceSubList = new ArrayList<>();
	
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
	
	public DigitalEntry(Market market, Asset asset, Speculate speculator){
		this.market = market;
		this.asset	= asset;
		this.speculator = speculator;
		setPriceSubList();
		setDate();
		setCurrentPrice();
		setMaxPrice();
		setMinPrice();
		setLocationAsIndex();
		setEntry();
		
		if(this.isEntry){
			setTrueRange();
			setStop();
			setUnitSize();
			setOrderTotal();
		}
	}

	@Override
	public void setEntry() {
		if(this.currentPrice.compareTo(this.maxPrice) == 0){
			this.isEntry = true;
			this.direction = Speculate.LONG;
		}else if(this.currentPrice.compareTo(this.minPrice) == 0){
			this.isEntry = true;
			this.direction = Speculate.SHORT;
		}else{
			this.isEntry = false;
		}
	}

	@Override
	public void setDate() {
		TimeZone timeZone = TimeZone.getTimeZone("UTC");
		Calendar calendar = Calendar.getInstance(timeZone);
		SimpleDateFormat simpleDateFormat = 
		       new SimpleDateFormat("MMM dd yyyy", Locale.US);
		simpleDateFormat.setTimeZone(timeZone);
		this.Date = simpleDateFormat.format(this.priceSubList.get(this.priceSubList.size() - 1).getDate()).toString();
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
	public BigDecimal getCurrentPrice() {
		return this.currentPrice;
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
	public BigDecimal getMaxPrice() {
		return this.maxPrice;
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
		this.priceSubList = (List<PoloniexChartData>) this.asset.getPriceSubList();
	}
	
	//True Range of prices per share, measured in Dollars per Share..if True Range is 1.25 it means max daily variations is $1.25 per share
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
		for(int x = Speculate.MOVING_AVG; x < this.getLocationIndex();x++){
			List<BigDecimal> trList = Arrays.asList(
					this.asset.getHighList().get(x).subtract(this.asset.getLowList().get(x).abs(), MathContext.DECIMAL32),
					this.asset.getHighList().get(x).subtract(this.asset.getCloseList().get(x-1).abs(), MathContext.DECIMAL32),
					this.asset.getCloseList().get(x-1).subtract(this.asset.getLowList().get(x).abs(), MathContext.DECIMAL32));
					
					tR = tR.multiply(new BigDecimal(Speculate.MOVING_AVG - 1), MathContext.DECIMAL32)
					.add((Collections.max(trList)), MathContext.DECIMAL32).
					divide(new BigDecimal(Speculate.MOVING_AVG), MathContext.DECIMAL32);
		}
		
		this.averageTrueRange = tR;
	}

	@Override
	public BigDecimal getTrueRange() {
		return this.averageTrueRange;
	}

	@Override
	public void setStop() {
		if(this.getDirection().equals(Speculate.LONG)){
			this.stop = this.getCurrentPrice().subtract(Speculate.STOP.multiply(this.getTrueRange(), MathContext.DECIMAL32));
		}else if(this.getDirection().equals(Speculate.SHORT)){
			this.stop = this.getCurrentPrice().add(Speculate.STOP.multiply(this.getTrueRange(), MathContext.DECIMAL32));
		}
		
	}

	@Override
	public BigDecimal getStop() {
		// TODO Auto-generated method stub
		return this.stop;
	}
	
	@Override
	public void setUnitSize() {
		BigDecimal max = this.speculator.getAccountEquity().divide(this.currentPrice, MathContext.DECIMAL32).setScale(0, RoundingMode.DOWN);
		BigDecimal size = this.speculator.getAccountEquity().multiply(Speculate.RISK, MathContext.DECIMAL32)
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
		sb.append("[ENTRY] ");
		sb.append(" [$" + this.asset.getAsset() + "]");
		sb.append(" Date: " + this.Date);
		sb.append(" Price:" + this.currentPrice);
		sb.append(" Direction:" + this.direction);
		sb.append(" ATR: " + this.averageTrueRange);
		sb.append(" Unit Size: " + this.unitSize);
		sb.append(" Total: " + this.orderTotal.setScale(8, RoundingMode.HALF_DOWN));
		sb.append(" Stop: " + this.stop.setScale(8, RoundingMode.HALF_DOWN));
		return sb.toString();
	}

	@Override
	public Date getDateTime() {
		String date = this.getDate();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date dateTime;
		try {
			dateTime = df.parse(date);
			return dateTime;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
