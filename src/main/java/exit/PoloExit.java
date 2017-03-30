package exit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;

import asset.PoloAsset;
import tradeold.TradingSystem;

public class PoloExit {
	
	public List<PoloniexChartData> exitList;
	
	public PoloExit(PoloAsset asset){
		this.exitList = exitList(asset.priceList);
	}
	
	public static List<PoloniexChartData> exitList(List<PoloniexChartData> priceList){
		List<PoloniexChartData> e = new ArrayList<>();
		int start = priceList.size() - 1;
		BigDecimal currentDay, previousDay;
		for(int x = start; x >= TradingSystem.CLOSE; x--){
					currentDay = priceList.get(start).getClose();
					previousDay = priceList.get(x - 1).getClose();
					if(currentDay.compareTo(previousDay) > 0){
						break;
					}
					if(x == start - TradingSystem.CLOSE && currentDay.compareTo(previousDay) < 0){
						e.add(priceList.get(start));
					}
					
		}
		return e;		
	}

}
