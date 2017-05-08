package price;

import java.math.BigDecimal;
import java.util.Date;

import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;

public class PriceList extends PoloniexChartData {
	
	public PriceList(Date date, BigDecimal high, BigDecimal low, BigDecimal open, BigDecimal close, BigDecimal volume,
			BigDecimal quoteVolume, BigDecimal weightedAverage) {
		super(date, high, low, open, close, volume, quoteVolume, weightedAverage);

	}
	
	@Override
	public String toString(){
		return this.getDate() + "," + this.getHigh() + "," + this.getLow() + "," + this.getOpen() + "," + this.getClose() + 
				"," + this.getVolume() + "," + this.getQuoteVolume() + "," + this.getWeightedAverage();
		
	}

}
