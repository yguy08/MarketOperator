package price;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import util.StringFormatter;

public class PriceData {
	
	private Date date;
	private BigDecimal high;
	private BigDecimal low;
	private BigDecimal open;
	private BigDecimal close;
	private BigDecimal volume;
	
	public PriceData(Date date, BigDecimal high, BigDecimal low, BigDecimal open, BigDecimal close, BigDecimal volume){
		this.date = date;
		this.high = high;
		this.low = low;
		this.open = open;
		this.close = close;
		this.volume = volume;
	}
	
	//create poloniex chart data from txt file string
	public static PriceData createOfflinePriceData(String[] chartData) throws ParseException{
		DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
		PriceData priceData = new PriceData(format.parse(chartData[0].trim()), new BigDecimal(chartData[1]), new BigDecimal(chartData[2]),
				new BigDecimal(chartData[3]), new BigDecimal(chartData[4]), new BigDecimal(chartData[5]));
		return priceData; 
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
	
	public static String prettyPrice(BigDecimal price){
		price = price.movePointRight(8);
		String pattern = "###,###,###";
		DecimalFormat decimalFormat = new DecimalFormat(pattern);
		String number = decimalFormat.format(price);
		return number;
	}
}
