package stocks;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class StockPriceList {
	static List<BigDecimal> closeList;
	static List<BigDecimal> highList;
	static List<BigDecimal> lowList;
	static List<String> dateList;
	
	StockPriceList(String name) throws IOException{
		String trimDate;
		String trimHigh;
		String trimLow;
		String trimClose;
		List<String> myString = FileParser.readYahooStockFileByLines(name);
		for(int z = 0; z < myString.size(); z++){
			List<String> myList = new ArrayList<>();
			String[] split = myString.get(z).split(",");
			for(String x : split){
				myList.add(x);
			}
			
			trimDate		= myList.get(0);
			trimHigh		= myList.get(2);
			trimLow			= myList.get(3);
			trimClose		= myList.get(4);
			
			setHighList(new BigDecimal(trimHigh));
			setLowList(new BigDecimal(trimLow));
			setCloseList(new BigDecimal(trimClose));
			setDateList(trimDate);}
	}
	
	public static void setStockListFromFile(String name) throws IOException{
		String trimDate;
		String trimHigh;
		String trimLow;
		String trimClose;
		List<String> myString = FileParser.readYahooStockFileByLines(name);
		for(int z = 0; z < myString.size(); z++){
			List<String> myList = new ArrayList<>();
			String[] split = myString.get(z).split(",");
			for(String x : split){
				myList.add(x);
			}
			
			trimDate		= myList.get(0);
			trimHigh		= myList.get(2);
			trimLow			= myList.get(3);
			trimClose		= myList.get(4);
			
			setHighList(new BigDecimal(trimHigh));
			setLowList(new BigDecimal(trimLow));
			setCloseList(new BigDecimal(trimClose));
			setDateList(trimDate);
		}
	}
	
	public static List<BigDecimal> getCloseList(){
		return closeList;
	}
	
	public static List<BigDecimal> getHighList(){
		return highList;
	}
	
	public static List<BigDecimal> getLowList(){
		return lowList;
	}
	
	public static List<String> getDateList(){
		return dateList;
	}

	public static void setDateList(String date){
		dateList.add(date);
	}
	
	public static void setHighList(BigDecimal bd){
		highList.add(bd);
		
	}
	
	public static void setLowList(BigDecimal bd){
		lowList.add(bd);
	}

	
	public static void setCloseList(BigDecimal bd){
		closeList.add(bd);
	}

}
