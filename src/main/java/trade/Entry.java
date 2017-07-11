package trade;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import asset.Asset;
import price.PriceData;
import speculator.Speculator;
import util.DateUtils;
import util.StringFormatter;
import vault.Config;
import vault.Displayable;
import vault.SymbolsEnum;

public class Entry implements Displayable {
	
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
		int days = DateUtils.getNumDaysFromDateToToday(getDateTime());
		if(days > Config.getTimeFrameDays()){
			return false;
		}
		
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
			if(Config.isLongOnly() && !(isLongEntry)){
				return false;
			}else if(Config.isFilterAssets()){
				for(String s : Config.getAssetFilter()){
					if(getAsset().getAssetName().contains(s)){
						return true;
					}
				}				
				return false;
			}else{
				boolean isHighEqualToLow = maxPrice.compareTo(minPrice) == 0;
				boolean isBelowVolumeFilter = getAsset().getVolumeFromIndex(locationIndex).compareTo(Config.getMinVolume()) < 0;
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
	
	public void setTrueRange() {
		averageTrueRange = getAsset().getPriceDataList().get(locationIndex).getTrueRange();
	}
	
	public BigDecimal getTrueRange() {
		return averageTrueRange;
	}
	
	public void setStop() {
		if(isLongEntry){
			stop = getAsset().getClosePriceFromIndex(locationIndex).subtract(Config.getStopLength().multiply(getTrueRange(), MathContext.DECIMAL32));
		}else{
			stop = getAsset().getClosePriceFromIndex(locationIndex).add(Config.getStopLength().multiply(getTrueRange(), MathContext.DECIMAL32));
		}
	}
	
	public BigDecimal getStop() {
		return stop;
	}
	
	public void setUnitSize() {
		BigDecimal max = speculator.getAccountBalance().divide(getAsset().getClosePriceFromIndex(locationIndex), MathContext.DECIMAL32).setScale(0, RoundingMode.DOWN);
		BigDecimal size = speculator.getAccountBalance().multiply(Config.getRisk().divide(new BigDecimal(100), MathContext.DECIMAL32), MathContext.DECIMAL32)
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
	
	public BigDecimal getVolume(){
		return getAsset().getVolumeFromIndex(locationIndex);
	}
	
	public Asset getAsset() {
		return asset;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(DateUtils.dateToMMddFormat(getAsset().getDateTimeFromIndex(entryIndex)) + " ");
		sb.append(prettyName());
		sb.append(" @" + PriceData.prettySatsPrice(getAsset().getClosePriceFromIndex(locationIndex)));
		sb.append(" " + SymbolsEnum.N.getSymbol() + StringFormatter.prettyPointX(averageTrueRange));
		sb.append(" " + SymbolsEnum.POUND.getSymbol() + unitSize);
		sb.append(" " + SymbolsEnum.TOTAL_COST.getSymbol() + orderTotal.setScale(2, RoundingMode.HALF_DOWN));
		sb.append(" " + SymbolsEnum.STOP.getSymbol() + StringFormatter.prettyPointX(stop));
		sb.append(" " + SymbolsEnum.VOLUME.getSymbol() + StringFormatter.bigDecimalToShortString(getAsset().getVolumeFromIndex(locationIndex).setScale(0, RoundingMode.HALF_DOWN)));		
		return sb.toString();
	}
	
	public int getEntryIndex() {
		return entryIndex;
	}
	
	public boolean isLongEntry() {
		return isLongEntry;
	}
	
	private String prettyName(){
		String arrow = (isLongEntry) ? "\u25B2" : "\u25BC";
		return "$" + getAsset().getAssetName().replace("/BTC", "") + arrow;
	}
	
	public BigDecimal getEntryPrice(){
		return getAsset().getClosePriceFromIndex(getEntryIndex());
	}
	
	public static List<Entry> getEntryList(Asset asset, Speculator speculator){
		Entry entry;
		List<Entry> entryList = new ArrayList<>();
		int i = asset.getStartIndex(Config.getEntrySignalDays(), Config.getTimeFrameDays()); 
		for(int x = i;x < asset.getPriceDataList().size();x++){
			asset.setPriceSubList(x - Config.getEntrySignalDays(), x);
			entry = new Entry(asset, speculator);
			if(entry.isEntry()){
				entryList.add(entry);
			}				
		}
		return entryList;
	}

}
