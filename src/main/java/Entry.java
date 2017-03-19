import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;
import org.knowm.xchange.utils.DateUtils;

public class Entry extends Asset {
	
	static List<PoloniexChartData>				entries = new ArrayList<>();
	String date;
	
	
	public Entry(String name, List<PoloniexChartData> priceList, Date date){
		super(name, priceList);
		this.date 		= DateUtils.toUTCString(date);
		highFinder(Asset.getPriceList(),date);
	}
    
    //get entry list
	public static void highFinder(List<PoloniexChartData> priceList, Date date){
		int start = 0;
		for(int x = 0; x < priceList.size();x++){
			String d = DateUtils.toUTCString(priceList.get(x).getDate());
			String f = DateUtils.toUTCString(date);
			if(d.equals(f)){
				start = x;
				break;
			}else{
				start = 0;
			}
		}
		
		if(start == 0){
			System.out.println("Bad Date!");
		}else{
			List<PoloniexChartData> e = new ArrayList<>();
			BigDecimal currentDay, previousDay;
			int count = 0;
			for(int x = start; x < priceList.size(); x++){
					currentDay = priceList.get(x).getClose();
					previousDay = priceList.get(x - 1).getClose();
					count = 0;
					if(currentDay.compareTo(previousDay) > 0){
						count++;
						for(int y = x - 2; y > 1; y--){
							previousDay = priceList.get(y).getClose();
							if(currentDay.compareTo(previousDay) == - 1){
								break;
							}else{
								count++;
							}if(count >= TradingSystem.HIGH_LOW){
								e.add(priceList.get(x));
								break;
							}
						}
					}
				}
			if(e.size() > 0){
			entries.addAll(e);
			System.out.println(e.size() + " Entries found!");
			}else{
				System.out.println("No entries found...");
			}
		}
	}
}
