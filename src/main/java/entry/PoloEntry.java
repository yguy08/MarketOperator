package entry;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;

import asset.PoloAsset;
import trade.TradingSystem;

public class PoloEntry {
	
	public List<PoloniexChartData> entryList;
	
	public PoloEntry(PoloAsset asset){
		this.entryList 	= entryFinder(asset.priceList);
	}
	
	public static List<PoloniexChartData> entryFinder(List<PoloniexChartData> priceList){
		List<PoloniexChartData> e = new ArrayList<>();
		int start = priceList.size() - 1;
		BigDecimal currentDay, previousDay;
			currentDay = priceList.get(start).getClose();
			previousDay = priceList.get(start - 1).getClose();
			if(currentDay.compareTo(previousDay) < 0){
				e = lowFinder(priceList);
			}
			else{
				e = highFinder(priceList);
			}
			return e;		
	}
	
	//get entry list
	public static List<PoloniexChartData> highFinder(List<PoloniexChartData> priceList){
		List<PoloniexChartData> e = new ArrayList<>();
		int start = priceList.size() - 1;
		BigDecimal currentDay, previousDay;
		for(int x = start; x >= TradingSystem.HIGH_LOW; x--){
			currentDay = priceList.get(start).getClose();
			previousDay = priceList.get(x - 1).getClose();
			if(currentDay.compareTo(previousDay) < 0){
				break;
			}
			if(x == start - TradingSystem.HIGH_LOW && currentDay.compareTo(previousDay) > 0){
				e.add(priceList.get(start));
			}
					
		}
		return e;
	}
	
	//get entry list
	public static List<PoloniexChartData> lowFinder(List<PoloniexChartData> priceList){
		List<PoloniexChartData> e = new ArrayList<>();
		int start = priceList.size() - 1;
		BigDecimal currentDay, previousDay;
		for(int x = start; x >= TradingSystem.HIGH_LOW; x--){
					currentDay = priceList.get(start).getClose();
					previousDay = priceList.get(x - 1).getClose();
					if(currentDay.compareTo(previousDay) > 0){
						break;
					}
					if(x == start - TradingSystem.HIGH_LOW && currentDay.compareTo(previousDay) < 0){
						e.add(priceList.get(start));
					}
		}
		return e;
	}
	
	public boolean isHigh(List<PoloniexChartData> priceList){
		int start = priceList.size() - 1;
		BigDecimal currentDay, previousDay;
		currentDay = priceList.get(start).getClose();
		previousDay = priceList.get(start - 1).getClose();
		if(currentDay.compareTo(previousDay) < 0){
			return false;
		}
		else{
			return true;
		}
	}
	
	
	
	

}
