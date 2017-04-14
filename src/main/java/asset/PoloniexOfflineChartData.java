package asset;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;

public class PoloniexOfflineChartData {
	  private Date date;
	  private BigDecimal high;
	  private BigDecimal low;
	  private BigDecimal open;
	  private BigDecimal close;
	  private BigDecimal volume;
	  private BigDecimal quoteVolume;
	  private BigDecimal weightedAverage;

	  @JsonCreator
	  public PoloniexOfflineChartData(Date date, BigDecimal high, BigDecimal low, BigDecimal open, 
			  BigDecimal close, BigDecimal volume, BigDecimal quoteVolume,BigDecimal weightedAverage) {
	    this.date = date;
	    this.high = high;
	    this.low = low;
	    this.open = open;
	    this.close = close;
	    this.volume = volume;
	    this.quoteVolume = quoteVolume;
	    this.weightedAverage = weightedAverage;
	  }

	  public Date getDate() {
	    return date;
	  }

	  public BigDecimal getHigh() {
	    return high;
	  }

	  public BigDecimal getLow() {
	    return low;
	  }

	  public BigDecimal getOpen() {
	    return open;
	  }

	  public BigDecimal getClose() {
	    return close;
	  }

	  public BigDecimal getVolume() {
	    return volume;
	  }

	  public BigDecimal getQuoteVolume() {
	    return quoteVolume;
	  }

	  public BigDecimal getWeightedAverage() {
	    return weightedAverage;
	  }

	  @Override
	  public String toString() {
	    return "PoloniexChartData [" + "date=" + date + ", high=" + high + ", low=" + low + ", open=" + open + ", close=" + close + ", volume=" + volume
	        + ", quoteVolume=" + quoteVolume + ", weightedAverage=" + weightedAverage + ']';
	  }
}
