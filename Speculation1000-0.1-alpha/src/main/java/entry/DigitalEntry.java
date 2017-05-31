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

import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;

import asset.Asset;
import market.Market;
import speculate.Speculate;
import utils.DateUtils;
import utils.StringFormatter;

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
	BigDecimal volume;
	
	String assetName;
		
	int locationIndex;
	String direction = null;
	Boolean isEntry = false;
	
	public DigitalEntry(Market market, Asset asset, Speculate speculator){
		this.market = market;
		this.asset	= asset;
		this.speculator = speculator;
		setAssetName(this.asset.getAsset());
		setPriceSubList();
		setDate(DateUtils.dateToSimpleDateFormat(this.priceSubList.get(this.priceSubList.size() - 1).getDate()));
		setCurrentPrice(this.priceSubList.get(this.priceSubList.size() - 1).getClose());
		setMaxPrice(this.priceSubList);
		setMinPrice(this.priceSubList);
		setVolume(this.priceSubList.get(this.priceSubList.size()-1).getVolume());
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
		//filters
		boolean isHighEqualToLow = this.maxPrice.compareTo(this.minPrice) == 0;
		boolean isBelowVolumeFilter = this.volume.compareTo(Speculate.VOLUME_FILTER) < 0;
		boolean isFilteredIn = !(isHighEqualToLow || isBelowVolumeFilter);
		
		boolean isEqualToHigh = this.currentPrice.compareTo(this.maxPrice) == 0;
		boolean isEqualToLow = this.currentPrice.compareTo(this.minPrice) == 0;
		
		this.isEntry = (isEqualToHigh || isEqualToLow) ? isFilteredIn : false;
		if(isEntry){
			this.direction = isEqualToHigh ? Speculate.LONG : Speculate.SHORT;
		}else{
			
		}
	}

	@Override
	public String getDate() {
		return this.Date;
	}
	
	@Override
	public void setDate(String date) {
		this.Date = date;
	}

	@Override
	public BigDecimal getCurrentPrice() {
		return this.currentPrice;
	}
	
	@Override
	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}

	@Override
	public void setMaxPrice(List<?> priceSubList) {
		List<BigDecimal> maxList = new ArrayList<>();
		List<PoloniexChartData> lastXDaysList = (List<PoloniexChartData>) priceSubList;
		for(int x = 0; x < lastXDaysList.size(); x++){
			maxList.add(lastXDaysList.get(x).getClose());
		}
		
		this.maxPrice = Collections.max(maxList);
	}

	@Override
	public BigDecimal getMaxPrice() {
		return this.maxPrice;
	}

	@Override
	public void setMinPrice(List<?> priceSubList) {
		List<BigDecimal> minList = new ArrayList<>();
		List<PoloniexChartData> lastXDaysList = (List<PoloniexChartData>) priceSubList;
		System.out.println("Finding min price for: " + lastXDaysList.get(0).toString());
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
		BigDecimal longStop = this.getCurrentPrice().subtract(Speculate.STOP.multiply(this.getTrueRange(), MathContext.DECIMAL32));
		BigDecimal shortStop = this.getCurrentPrice().add(Speculate.STOP.multiply(this.getTrueRange(), MathContext.DECIMAL32));
		boolean isLong = this.getDirection().equals(Speculate.LONG);
		this.stop = isLong ? longStop : shortStop;
	}

	@Override
	public BigDecimal getStop() {
		return this.stop;
	}
	
	@Override
	public void setUnitSize(Speculate speculate) {
		BigDecimal max = speculate.getAccountEquity().divide(this.currentPrice, MathContext.DECIMAL32).setScale(0, RoundingMode.DOWN);
		BigDecimal size = speculate.getAccountEquity().multiply(Speculate.RISK, MathContext.DECIMAL32)
				.divide(this.averageTrueRange, MathContext.DECIMAL32).setScale(0, RoundingMode.DOWN);
		this.unitSize = (size.compareTo(max) > 0) ? max : size;

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
		sb.append(" [$" + this.getAssetName() + "]");
		sb.append(" Date: " + this.getDate());
		sb.append(" Price:" + StringFormatter.bigDecimalToEightString(this.currentPrice));
		sb.append(" Direction:" + this.direction);
		sb.append(" ATR: " + StringFormatter.bigDecimalToEightString(this.averageTrueRange));
		sb.append(" Unit Size: " + this.unitSize);
		sb.append(" Total: " + this.orderTotal.setScale(8, RoundingMode.HALF_DOWN));
		sb.append(" Stop: " + StringFormatter.bigDecimalToEightString(this.stop));
		sb.append(" Volume: " + StringFormatter.bigDecimalToEightString(this.getVolume()));
		return sb.toString();
	}

	@Override
	public Date getDateTime() {
		String date = this.getDate();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date dateTime;
		try {
			dateTime = df.parse(date);
			return DateUtils.dateToUTCMidnight(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public BigDecimal getVolume() {
		return this.volume;
	}
	
	@Override
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	@Override
	public boolean isLong() {
		return (this.direction == Speculate.LONG) ? true : false;
	}

	@Override
	public Entry copy(Entry entry, Speculate speculator) {
		Entry digitalEntry = new DigitalEntry();
		digitalEntry.setDate(entry.getDate());
		digitalEntry.setDirection(entry.getDirection());
		digitalEntry.setCurrentPrice(entry.getCurrentPrice());
		digitalEntry.setTrueRange(entry.getTrueRange());
		digitalEntry.setStop(entry.getStop());
		digitalEntry.setUnitSize(speculator);
		digitalEntry.setOrderTotal();
		digitalEntry.setAssetName(entry.getAssetName());
		digitalEntry.setVolume(entry.getVolume());
		return digitalEntry;
	}
	
	public DigitalEntry(){
		
	}

	@Override
	public void setTrueRange(BigDecimal trueRange) {
		this.averageTrueRange = trueRange;
	}

	@Override
	public void setStop(BigDecimal stop) {
		this.stop = stop;
	}

	@Override
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	@Override
	public String getAssetName() {
		return this.assetName;
	}
	
	@Override
	public void setDirection(String direction){
		//make sure direction is formatted correctly before assigning
		boolean isFormat = (direction == Speculate.LONG || direction == Speculate.SHORT) ? true : false;
		if(isFormat){
			this.direction = direction;
		}
	}

}
