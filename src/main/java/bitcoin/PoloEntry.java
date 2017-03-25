package bitcoin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;

import operator.TradingSystem;

public class PoloEntry {
	
	String name;
	public List<PoloniexChartData> entryList;
	
	public PoloEntry(PoloAsset asset){
		this.name 		= asset.name;
		this.entryList 	= highFinder(asset.priceList);
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

}
