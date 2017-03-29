package asset;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import fileparser.FileParser;

public class StockPriceList {

	List<StockChartData> stockPriceList = new ArrayList<>();
	
	StockPriceList(String name) throws IOException{
		List<String> myString = FileParser.readYahooStockFileByLines(name);
		for(int z = 0; z < myString.size(); z++){
			String[] split = myString.get(z).split(",");
			
			StockChartData chartData = new StockChartData((String) split[0], new BigDecimal(split[2]), 
					new BigDecimal(split[3]), new BigDecimal(split[4]));
			stockPriceList.add(chartData);
		}
	}
	
	class StockChartData{
		
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
		
		@Override
		public String toString(){
			return date + " " + close;
		}
	}
	
}
