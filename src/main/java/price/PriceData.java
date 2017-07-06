package price;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import util.DateUtils;
import vault.Config;

public class PriceData {
	
	private Date date;
	
	private BigDecimal high;
	
	private BigDecimal low;
	
	private BigDecimal open;
	
	private BigDecimal close;
	
	private BigDecimal volume;
	
	private BigDecimal trueRange;
	
	public PriceData(Date date, BigDecimal high, BigDecimal low, BigDecimal open, BigDecimal close, BigDecimal volume){
		this.date = date;
		this.high = high;
		this.low = low;
		this.open = open;
		this.close = close;
		this.volume = volume;
	}
	
	public PriceData(String[] dayPriceDataArr) {
		try{
			//only need if date is weird in file...
			DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");			
			setDate(format.parse(dayPriceDataArr[0].trim()));
		}catch(ParseException e){
			System.out.println("Error parsing date in price data array");
		}
		
		high = new BigDecimal(dayPriceDataArr[1]);
		low = new BigDecimal(dayPriceDataArr[2]);
		open =	new BigDecimal(dayPriceDataArr[3]);
		close = new BigDecimal(dayPriceDataArr[4]); 
		volume = new BigDecimal(dayPriceDataArr[5]);
		
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getHigh() {
		return high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	public BigDecimal getLow() {
		return low;
	}

	public void setLow(BigDecimal low) {
		this.low = low;
	}

	public BigDecimal getOpen() {
		return open;
	}

	public void setOpen(BigDecimal open) {
		this.open = open;
	}

	public BigDecimal getClose() {
		return close;
	}

	public void setClose(BigDecimal close) {
		this.close = close;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public BigDecimal getTrueRange() {
		return trueRange;
	}
	
	public void setTrueRange(BigDecimal trueRange){
		this.trueRange = trueRange;
	}

	public static void setTrueRange(List<PriceData> priceDataList) {
		int movingAvg = Config.getMovingAvg();		
		//set first TR for 0 position (H-L)
		BigDecimal tR = priceDataList.get(0).getHigh().subtract(priceDataList.get(0).getClose()).abs();
		priceDataList.get(0).setTrueRange(tR);		
		for(int x = 1; x < movingAvg; x++){
			List<BigDecimal> trList = Arrays.asList(
					priceDataList.get(x).getHigh().subtract(priceDataList.get(x).getLow().abs(), MathContext.DECIMAL32),
					priceDataList.get(x).getHigh().subtract(priceDataList.get(x-1).getClose().abs(), MathContext.DECIMAL32),
					priceDataList.get(x-1).getClose().subtract(priceDataList.get(x).getLow().abs(), MathContext.DECIMAL32));				
				tR = tR.add(Collections.max(trList));
		}		
		tR = tR.divide(new BigDecimal(movingAvg), MathContext.DECIMAL32);		
		//initial up to MA get the same
		for(int x=1;x<movingAvg;x++){
			priceDataList.get(x).setTrueRange(tR);
		}		
		//20 exponential moving average
		for(int x = movingAvg; x < priceDataList.size();x++){
			List<BigDecimal> trList = Arrays.asList(
					priceDataList.get(x).getHigh().subtract(priceDataList.get(x).getLow().abs(), MathContext.DECIMAL32),
					priceDataList.get(x).getHigh().subtract(priceDataList.get(x-1).getClose().abs(), MathContext.DECIMAL32),
					priceDataList.get(x-1).getClose().subtract(priceDataList.get(x).getLow().abs(), MathContext.DECIMAL32));					
					tR = tR.multiply(new BigDecimal(movingAvg - 1), MathContext.DECIMAL32)
					.add((Collections.max(trList)), MathContext.DECIMAL32).
					divide(new BigDecimal(movingAvg), MathContext.DECIMAL32);					
					priceDataList.get(x).setTrueRange(tR);
		}
	}
	
	public static String prettyPrice(BigDecimal price){
		price = price.movePointRight(8);
		String pattern = "###,###,###";
		DecimalFormat decimalFormat = new DecimalFormat(pattern);
		String number = decimalFormat.format(price);
		return number;
	}
	
	@Override
	public String toString(){
		return this.getDate() + "," + this.getHigh() + "," + this.getLow() + "," + this.getOpen() + "," + this.getClose() + 
				"," + this.getVolume() + "," + this.getTrueRange();
	}
}
