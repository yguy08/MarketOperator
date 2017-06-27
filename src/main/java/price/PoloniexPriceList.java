package price;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;

public class PoloniexPriceList extends PoloniexChartData {
	
	//Poloniex Price List constructor to save to file...
	public PoloniexPriceList(Date date, BigDecimal high, BigDecimal low, BigDecimal open, BigDecimal close, BigDecimal volume,
			BigDecimal quoteVolume, BigDecimal weightedAverage) {
		super(date, high, low, open, close, volume, quoteVolume, weightedAverage);
	}
	
	//create poloniex chart data from txt file string
	public static PoloniexChartData createPoloOfflinePriceList(String[] chartData) throws ParseException{
		DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
		PoloniexChartData pl = new PoloniexChartData(format.parse(chartData[0].trim()), new BigDecimal(chartData[1]), new BigDecimal(chartData[2]),
				new BigDecimal(chartData[3]), new BigDecimal(chartData[4]), new BigDecimal(chartData[5]), new BigDecimal(0.00), 
				new BigDecimal(0.00));
		return pl; 
	}
	
	@Override
	public String toString(){
		return this.getDate() + "," + this.getHigh() + "," + this.getLow() + "," + this.getOpen() + "," + this.getClose() + 
				"," + this.getVolume() + "," + this.getQuoteVolume() + "," + this.getWeightedAverage();
	}

}
