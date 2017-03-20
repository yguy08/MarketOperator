import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;
import org.knowm.xchange.utils.DateUtils;

public class Entry extends Asset {
	
	static List<PoloniexChartData>				entries = new ArrayList<>();
	String date;
	private String name;
	List<PoloniexChartData> entryList = new ArrayList<>();
	
	
	public Entry(String name, List<PoloniexChartData> priceList, Date date){
		super(name, priceList);
		this.date 		= DateUtils.toUTCString(date);
		this.name = super.getName();
		this.entryList = highFinder(Asset.getPriceList(),date);
		//highFinder(Asset.getPriceList(),date);
	}
	
    
    //get entry list
	public static List<PoloniexChartData> highFinder(List<PoloniexChartData> priceList, Date date){
		List<PoloniexChartData> e = new ArrayList<>();
		int start = 0;
		for(int x = 0; x < priceList.size();x++){
			String d = DateUtils.toUTCString(priceList.get(x).getDate());
			String f = DateUtils.toUTCString(date);
			if(d.equals(f)){
				start = x;
				break;
			}else{
				start = 1;
			}
		}
		
		if(start == 1){
			System.out.println("Date out of range or bad! Starting at first available");
		}
		
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
			for(int p = 0; p < e.size();p++){
			//System.out.println(e.size() + " Entries found!");
			}
			
			}else{
				//e.add(priceList.get(priceList.size() - 1));
				System.out.println("No entries found...");
			}
		return e;
	}
}
