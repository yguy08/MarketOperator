package trade;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Collections;
import java.util.Date;

import asset.Asset;
import price.PriceData;
import speculator.Speculator;
import util.DateUtils;

public class Exit {
	
	Entry entry;
	
	Asset asset;
	
	Speculator speculator;
	
	boolean isExit = false;
	
	boolean isOpen = false;
	
	int locationIndex;
	
	public Exit(Entry entry, Speculator speculator){
		this.entry = entry;
		this.asset = entry.getAsset();
		this.speculator = speculator;
		this.locationIndex = asset.getIndexOfLastRecordInSubList();
		exitOrOpen();
	}
	
	private boolean exitOrOpen(){		
		BigDecimal currentPrice = asset.getClosePriceFromIndex(entry.getAsset().getIndexOfLastRecordInSubList());
		BigDecimal maxPrice = Collections.max(asset.getClosePriceListFromSubList());
		BigDecimal minPrice = Collections.min(asset.getClosePriceListFromSubList());
		
		if(entry.isLongEntry()){
			boolean isPriceALow 	= currentPrice.compareTo(minPrice) == 0;
			boolean isBelowStop 	= currentPrice.compareTo(entry.getStop()) < 0;
			if(isPriceALow || isBelowStop){
				isExit = true;
				return true;
			}else if(locationIndex == asset.getIndexOfLastRecordInPriceList()){
				isOpen = true;
				return false;
			}else{
				return false;
			}
		}else{
			boolean isPriceAHigh = currentPrice.compareTo(maxPrice) == 0;
			boolean isAboveStop = currentPrice.compareTo(entry.getStop()) > 0;
			if(isPriceAHigh || isAboveStop){
				isExit = true;
				return true;
			}else if(locationIndex == asset.getIndexOfLastRecordInPriceList()){
				isOpen = true;
				return false;
			}else{
				return false;
			}
		}
	}
	
	public boolean isOpen(){
		return isOpen;
	}
	
	public boolean isExit(){
		return isExit;
	}
	
	public Date getDateTime(){
		return entry.getAsset().getDateTimeFromIndex(locationIndex);
	}
	
	public Date getEntryDate(){
		return entry.getAsset().getDateTimeFromIndex(entry.getEntryIndex());
	}
	
	public BigDecimal getExitPrice(){
		return entry.getAsset().getClosePriceFromIndex(locationIndex);
	}
	
	public Entry getEntry(){
		return entry;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(DateUtils.dateToMMddFormat(getDateTime()));
		sb.append(prettyName());
		sb.append(" @" + PriceData.prettyPrice(getExitPrice()));
		sb.append(" \u2600" + DateUtils.dateToMMddFormat(entry.getDateTime()));
		sb.append(" @" + PriceData.prettyPrice(entry.getAsset().getClosePriceFromIndex(entry.getEntryIndex())));
		sb.append(openOrExit() + " ");
		sb.append("(" + PriceData.prettyPrice(calcGainLossAmount()) + ")");
		return  sb.toString();
	}
	
	private String prettyName(){
		String entryArrow = (entry.isLongEntry()) ? "\u25B2" : "\u25BC";
		int difEntryPrice = getExitPrice().compareTo(entry.getEntryPrice());
		boolean isUp = entry.isLongEntry() ? difEntryPrice >= 0 : difEntryPrice <= 0;
		String resultsArrow = isUp ? "\u25B2" : "\u25BC"; 
		return " $" + entry.getAsset().getAssetName().replace("/BTC", "") + entryArrow + resultsArrow;
	}
	
	private String openOrExit(){
		if(isExit()){
			return " C";
		}else if(isOpen()){
			return " O";
		}else{
			return " Error";
		}
	}
	
	public BigDecimal calcGainLossPercent(){
		BigDecimal percent = getExitPrice().subtract(entry.getEntryPrice())
				.divide(entry.getEntryPrice(), MathContext.DECIMAL32);
		BigDecimal profitLostPercent = (entry.isLongEntry()) ? percent : percent.negate();
		return profitLostPercent;
	}
	
	public BigDecimal calcGainLossAmount(){
		return entry.getOrderTotal().multiply(calcGainLossPercent(), MathContext.DECIMAL32);
	}

}
