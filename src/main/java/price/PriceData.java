package price;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	
	public PriceData(String[] singleDayPriceDataStrArr) {
		try{
			//only need if date is weird in file...
			DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");			
			setDate(format.parse(singleDayPriceDataStrArr[0].trim()));
		}catch(ParseException e){
			System.out.println("Error parsing date in price data array");
		}
		
		high = new BigDecimal(singleDayPriceDataStrArr[1]);
		low = new BigDecimal(singleDayPriceDataStrArr[2]);
		open =	new BigDecimal(singleDayPriceDataStrArr[3]);
		close = new BigDecimal(singleDayPriceDataStrArr[4]); 
		volume = new BigDecimal(singleDayPriceDataStrArr[5]);		
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
	
	public static String prettySatsPrice(BigDecimal price){
		price = price.movePointRight(8);
		String pattern = "###,###,###";
		DecimalFormat decimalFormat = new DecimalFormat(pattern);
		String number = decimalFormat.format(price);
		return number;
	}
	
	public static String prettyUSDPrice(BigDecimal price){
		String pattern = "0.00";
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
