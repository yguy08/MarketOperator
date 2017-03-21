package stocks;
import java.math.BigDecimal;

public class StockChartData {
	private String date;
	private BigDecimal close;
	private BigDecimal high;
	private BigDecimal low;
	
	public StockChartData(String date, BigDecimal close, BigDecimal high, BigDecimal low){
		this.date = date;
		this.close = close;
		this.high = high;
		this.low = low;
	}
	
	public String getDate(){
		return date;
	}
	
	public BigDecimal getHigh(){
		return high;
	}
	
	public BigDecimal getLow(){
		return low;
	}
	
	public BigDecimal getClose(){
		return close;
	}
	

}
