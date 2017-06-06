package entry;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import asset.Asset;
import speculator.Speculator;
import util.DateUtils;
import util.StringFormatter;

public class Entry {
	
	private Asset asset;
	
	private Speculator speculator;
	
	private BigDecimal averageTrueRange;
	
	private BigDecimal stop;
	
	private BigDecimal unitSize;
	
	private BigDecimal orderTotal;
		
	private int locationIndex;
	
	private int entryIndex;
	
	//true = long, false = short
	private boolean isLongEntry;
	
	private Boolean isEntry = false;
	
	public Entry(Asset asset, Speculator speculator){
		this.asset	= asset;
		this.speculator = speculator;
		this.locationIndex = getAsset().getIndexOfLastRecordInSubList(); 
		
		if(isEntryCandidate()){
			isEntry = true;
			entryIndex = locationIndex;
			setTrueRange();
			setStop();
			setUnitSize();
			setOrderTotal();
		}
	}
	
	//before anything else..check if price makes it an entry or not
	private boolean isEntryCandidate(){
		BigDecimal currentPrice = getAsset().getClosePriceFromIndex(locationIndex);
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
				boolean isBelowVolumeFilter = getAsset().getVolumeFromIndex(locationIndex).compareTo(speculator.getMinVolume()) < 0;
				boolean isFilteredIn = !(isHighEqualToLow || isBelowVolumeFilter);
				return isFilteredIn;
			}
			
		}else{
			return false;
		}
	}
	
	public int getLocationIndex() {
		return locationIndex;
	}
	
	public Boolean isEntry() {
		return isEntry;
	}
	
	//True Range of prices per share, measured in Dollars per Share..if True Range is 1.25 it means max daily variations is $1.25 per share
	//Move to ASSET??
	public void setTrueRange() {
		//consider instance where list is too small...
		if(asset.getPriceList().size() < Speculator.MOVING_AVG){
			//skip?
		}
		
		//set first TR for 0 position (H-L)
		BigDecimal tR = asset.getHighPriceFromIndex(0).subtract(asset.getClosePriceFromIndex(0)).abs();
		for(int x = 1; x < Speculator.MOVING_AVG; x++){
			List<BigDecimal> trList = Arrays.asList(
					asset.getHighPriceFromIndex(x).subtract(asset.getLowPriceFromIndex(x).abs(), MathContext.DECIMAL32),
					asset.getHighPriceFromIndex(x).subtract(asset.getClosePriceFromIndex(x-1).abs(), MathContext.DECIMAL32),
					asset.getClosePriceFromIndex(x-1).subtract(asset.getLowPriceFromIndex(x).abs(), MathContext.DECIMAL32));
				
				tR = tR.add(Collections.max(trList));
		}
		
		tR = tR.divide(new BigDecimal(Speculator.MOVING_AVG), MathContext.DECIMAL32);
		
		//20 exponential moving average
		for(int x = Speculator.MOVING_AVG; x < locationIndex;x++){
			List<BigDecimal> trList = Arrays.asList(
					asset.getHighPriceFromIndex(x).subtract(asset.getLowPriceFromIndex(x).abs(), MathContext.DECIMAL32),
					asset.getHighPriceFromIndex(x).subtract(asset.getClosePriceFromIndex(x-1).abs(), MathContext.DECIMAL32),
					asset.getClosePriceFromIndex(x-1).subtract(asset.getLowPriceFromIndex(x).abs(), MathContext.DECIMAL32));
					
					tR = tR.multiply(new BigDecimal(Speculator.MOVING_AVG - 1), MathContext.DECIMAL32)
					.add((Collections.max(trList)), MathContext.DECIMAL32).
					divide(new BigDecimal(Speculator.MOVING_AVG), MathContext.DECIMAL32);
		}
		
		this.averageTrueRange = tR;
	}
	
	public BigDecimal getTrueRange() {
		return averageTrueRange;
	}
	
	public void setStop() {
		if(isLongEntry){
			stop = getAsset().getClosePriceFromIndex(locationIndex).subtract(Speculator.STOP.multiply(getTrueRange(), MathContext.DECIMAL32));
		}else{
			stop = getAsset().getClosePriceFromIndex(locationIndex).add(Speculator.STOP.multiply(getTrueRange(), MathContext.DECIMAL32));
		}
	}
	
	public BigDecimal getStop() {
		return stop;
	}
	
	public void setUnitSize() {
		BigDecimal max = speculator.getAccountBalance().divide(getAsset().getClosePriceFromIndex(locationIndex), MathContext.DECIMAL32).setScale(0, RoundingMode.DOWN);
		BigDecimal size = speculator.getAccountBalance().multiply(Speculator.RISK, MathContext.DECIMAL32)
				.divide(averageTrueRange, MathContext.DECIMAL32).setScale(0, RoundingMode.DOWN);
		unitSize = (size.compareTo(max) > 0) ? max : size;
	}
	
	public BigDecimal getUnitSize() {
		return unitSize;
	}
	
	public void setOrderTotal() {
		orderTotal = unitSize.multiply(getAsset().getClosePriceFromIndex(locationIndex), MathContext.DECIMAL32);
	}
	
	public BigDecimal getOrderTotal() {
		return orderTotal;
	}
	
	public Date getDateTime() {
		return getAsset().getDateTimeFromIndex(locationIndex);
	}
	
	public Asset getAsset() {
		return asset;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(DateUtils.dateToMMddFormat(getAsset().getDateTimeFromIndex(entryIndex)) + " ");
		sb.append("$" + getAsset().getAssetName().replace("/BTC", ""));
		if(isLongEntry){
			sb.append("\u25B2");
		}else{
			sb.append("\u25BC");
		}
		sb.append(" @" + StringFormatter.bigDecimalToEightString(getAsset().getClosePriceFromIndex(locationIndex)));
		sb.append(" N" + StringFormatter.bigDecimalToEightString(this.averageTrueRange));
		sb.append(" \u0023" + this.unitSize);
		sb.append(" \u03A3" + this.orderTotal.setScale(2, RoundingMode.HALF_DOWN));
		sb.append(" \u2702" + StringFormatter.bigDecimalToEightString(this.stop));
		sb.append(" \uD83D\uDD0A" + StringFormatter.bigDecimalToShortString(getAsset().getVolumeFromIndex(locationIndex)));		
		return sb.toString();
	}
	
	public int getEntryIndex() {
		return entryIndex;
	}
	
	public boolean isLongEntry() {
		return isLongEntry;
	}

}
