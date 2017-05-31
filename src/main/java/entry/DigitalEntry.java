package entry;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import asset.Asset;
import speculator.Speculator;
import util.DateUtils;
import util.StringFormatter;

public class DigitalEntry implements Entry {
	
	private Asset asset;
	
	private Speculator speculator;
	
	private BigDecimal averageTrueRange;
	
	private BigDecimal stop;
	
	private BigDecimal unitSize;
	
	private BigDecimal orderTotal;
	
	private BigDecimal volume;
		
	private int locationIndex;
	
	//true = long, false = short
	private boolean isLongEntry;
	
	private Boolean isEntry = false;
	
	public DigitalEntry(Asset asset, Speculator speculator){
		this.asset	= asset;
		this.speculator = speculator;
		this.locationIndex = getAsset().getIndexOfCurrentRecordFromSubList(); 
		
		if(isEntryCandidate()){
			isEntry = true;
			setVolume(getAsset().getCurrentVolumeFromSubList());
			setTrueRange();
			setStop();
			setUnitSize();
			setOrderTotal();
		}
	}
	
	//before anything else..check if price makes it an entry or not
	private boolean isEntryCandidate(){
		BigDecimal currentPrice = getAsset().getCurrentPriceFromSubList();
		BigDecimal maxPrice = Collections.max(getAsset().getClosePriceListFromSubList());
		BigDecimal minPrice = Collections.min(getAsset().getClosePriceListFromSubList());
		
		//long
		boolean isPriceAHigh = currentPrice.compareTo(maxPrice) == 0;
		//short
		boolean isPriceALow = currentPrice.compareTo(minPrice) == 0;
		
		if(isPriceAHigh || isPriceALow){
			isLongEntry = isPriceAHigh ? true : false;
			//filters
			if(speculator.isLongOnly() && !(isLongEntry)){
				return false;
			}else{
				boolean isHighEqualToLow = maxPrice.compareTo(minPrice) == 0;
				boolean isBelowVolumeFilter = getAsset().getCurrentVolumeFromSubList().compareTo(speculator.getMinVolume()) < 0;
				boolean isFilteredIn = !(isHighEqualToLow || isBelowVolumeFilter);
				return isFilteredIn;
			}
			
		}else{
			return false;
		}
	}
	
	@Override
	public int getLocationIndex() {
		return locationIndex;
	}

	@Override
	public Boolean isEntry() {
		return isEntry;
	}
	
	//True Range of prices per share, measured in Dollars per Share..if True Range is 1.25 it means max daily variations is $1.25 per share
	//Move to ASSET??
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
		for(int x = Speculator.MOVING_AVG; x < locationIndex;x++){
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
		if(isLongEntry){
			stop = getAsset().getCurrentPriceFromSubList().subtract(Speculator.STOP.multiply(getTrueRange(), MathContext.DECIMAL32));
		}else{
			stop = getAsset().getCurrentPriceFromSubList().add(Speculator.STOP.multiply(getTrueRange(), MathContext.DECIMAL32));
		}
	}

	@Override
	public BigDecimal getStop() {
		return stop;
	}
	
	@Override
	public void setUnitSize() {
		BigDecimal max = speculator.getAccountBalance().divide(getAsset().getCurrentPriceFromSubList(), MathContext.DECIMAL32).setScale(0, RoundingMode.DOWN);
		BigDecimal size = speculator.getAccountBalance().multiply(Speculator.RISK, MathContext.DECIMAL32)
				.divide(averageTrueRange, MathContext.DECIMAL32).setScale(0, RoundingMode.DOWN);
		unitSize = (size.compareTo(max) > 0) ? max : size;
	}
	
	@Override
	public BigDecimal getUnitSize() {
		return unitSize;
	}
	
	@Override
	public void setOrderTotal() {
		orderTotal = unitSize.multiply(getAsset().getCurrentPriceFromSubList(), MathContext.DECIMAL32);
	}
	
	@Override
	public BigDecimal getOrderTotal() {
		return orderTotal;
	}

	@Override
	public Date getDateTime() {
		String date = getAsset().getDateStringFromIndex(locationIndex);
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
		return volume;
	}
	
	@Override
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	@Override
	public void setTrueRange(BigDecimal trueRange) {
		averageTrueRange = trueRange;
	}

	@Override
	public void setStop(BigDecimal stop) {
		this.stop = stop;
	}

	@Override
	public Asset getAsset() {
		return asset;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("$" + getAsset().getAssetName().replace("/BTC", ""));
		sb.append(" " + DateUtils.dateToMMddFormat(this.getDateTime()));
		sb.append(" Price:" + StringFormatter.bigDecimalToEightString(getAsset().getClosePriceFromIndex(locationIndex)));
		if(isLongEntry){
			sb.append(" ^ ");
		}else{
			sb.append(" v ");
		}
		sb.append(" ATR: " + StringFormatter.bigDecimalToEightString(this.averageTrueRange));
		sb.append(" Units: " + this.unitSize);
		sb.append(" Cost: " + this.orderTotal.setScale(2, RoundingMode.HALF_DOWN));
		sb.append(" Stop: " + StringFormatter.bigDecimalToEightString(this.stop));
		sb.append(" Volume: " + StringFormatter.bigDecimalToShortString(this.getVolume()));
		return sb.toString();
	}

}
