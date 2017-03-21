import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;

public class Entry extends Asset {
	
	String name;
	List<PoloniexChartData> entryList = new ArrayList<>();
	
	public Entry(String name, List<PoloniexChartData> priceList){
		super(name, priceList);
		this.name 		= name;
		this.entryList 	= highFinder(this.getPriceList());
	}
	
    
    //get entry list
	public static List<PoloniexChartData> highFinder(List<PoloniexChartData> priceList){
		List<PoloniexChartData> e = new ArrayList<>();
		int start = priceList.size() - 1;
		BigDecimal currentDay, previousDay;
		for(int x = start; x >= 1; x--){
					currentDay = priceList.get(start).getClose();
					previousDay = priceList.get(x - 1).getClose();
					if(currentDay.compareTo(previousDay) < 0){
						break;
					}
					if(x == 1 && currentDay.compareTo(previousDay) > 0){
						e.add(priceList.get(start));
					}
					
		}
		return e;

	}
}

