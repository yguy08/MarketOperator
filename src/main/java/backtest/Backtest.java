package backtest;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class Backtest {
	
	public static String runBackTest(List<BigDecimal> closeList){
		int nextEntry = 0;
		if(closeList.size() < 20){
			return "Not enough data!";
		}else{
			for(int x = 20; x < closeList.size();x++){
				nextEntry = getNextHighLow(closeList, x);
				break;
			}
		}
		
		return "Next high or low is: " + nextEntry;
	}
	
	public static int getNextHighLow(List<BigDecimal> closeList, int start){
		
		for(int x = start; x < closeList.size();x++){
			List<BigDecimal> subList = closeList.subList(x - 20, x);
			if(subList.get(subList.size() - 1).equals(Collections.max(subList))){
				return x;
			}else if(subList.get(subList.size() - 1).equals(Collections.min(subList))){
				return x;
			}
		}
		
		return closeList.size();
	}
	
	public static BigDecimal calcATR(List<BigDecimal> closeList, int start){
		return new BigDecimal(0.00);
	}
	
	public static BigDecimal calcPositionSize(List<BigDecimal> closeList, int start, BigDecimal atr){
		return new BigDecimal(0.00);
	}
	
	public static boolean isAdd(List<BigDecimal> closeList, int start, BigDecimal atr, BigDecimal positionSize){
		return false;
	}
	
	public static boolean isClose(List<BigDecimal> closeList, int start, BigDecimal atr, BigDecimal positionSize){
		return false;
	}
	
	

}
