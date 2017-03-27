package backtest;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ForexList {
	
	static List<String> dateList = new ArrayList<>();
	static List<BigDecimal> closeList = new ArrayList<>();
	
	public ForexList(String name) throws IOException{
		String trimDate;
		String trimClose;
		List<String> myString = FileParser.readFedForexByLines(name);
		
		for(int z = 0; z < myString.size(); z++){
			List<String> myList = new ArrayList<>();
			String[] split = myString.get(z).split(",");
			for(String x : split){
				myList.add(x);
			}
			
			trimDate		= myList.get(0);
			trimClose		= myList.get(1);
			
			setCloseList(new BigDecimal(trimClose));
			setDateList(trimDate);
		}
		
		dateList = getDateList();
		closeList = getCloseList();
	}
	
	public static List<BigDecimal> getCloseList(){
		return closeList;
	}
	
	public static List<String> getDateList(){
		return dateList;
	}

	public static void setDateList(String date){
		dateList.add(date);
	}

	
	public static void setCloseList(BigDecimal bd){
		closeList.add(bd);
	}
	

}
