import java.math.BigDecimal;

public class StockChartData {
	private String date;
	private BigDecimal close;
	
	public StockChartData(String date, BigDecimal close){
		this.date = date;
		this.close = close;
	}
	
	public String getDate(){
		return date;
	}
	
	public BigDecimal getClose(){
		return close;
	}
	

}
