package stocks;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import operator.TradingSystem;

public class StockEntry extends StockAsset {
	
	String name;
	public List<StockChartData> entryList;
	
	public StockEntry(String name, List<StockChartData> priceList){
		super(name, priceList);
		this.name 		= name;
		this.entryList 	= lastHighFinder(this.getPriceList());
	}
	
    
    //get entry list
	public static List<StockChartData> highFinder(List<StockChartData> priceList){
		List<StockChartData> e = new ArrayList<>();
		int start = 0;
		BigDecimal currentDay, previousDay;
		for(int x = start; x < TradingSystem.HIGH_LOW; x++){
					currentDay = priceList.get(start).getClose();
					previousDay = priceList.get(x + 1).getClose();
					if(currentDay.compareTo(previousDay) < 0){
						break;
					}
					if(x == TradingSystem.HIGH_LOW - 1 && currentDay.compareTo(previousDay) > 0){
						e.add(priceList.get(start));
					}
					
		}
		return e;
	}
	
	public static List<StockChartData> lastHighFinder(List<StockChartData> priceList){
		List<StockChartData> e = new ArrayList<>();
		int start = 0;
		BigDecimal currentDay, previousDay;
		for(int x = start; x < TradingSystem.HIGH_LOW; x++){
			currentDay = priceList.get(start).getClose();
			previousDay = priceList.get(x + 1).getClose();
			if(currentDay.compareTo(previousDay) < 0){
				e.add(priceList.get(x + 1));
				break;
			}
			if(x == TradingSystem.HIGH_LOW - 1 && currentDay.compareTo(previousDay) > 0){
				e.add(priceList.get(start));
			}
			
		}
			return e;
	}

}
