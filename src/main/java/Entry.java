import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;
import org.knowm.xchange.utils.DateUtils;

public class Entry {
	
	static String 							asset;
	static List<PoloniexChartData>			priceList = new ArrayList<>();
	static List<PoloniexChartData>			entryList = new ArrayList<>();

	
	public Entry(String asset, List<PoloniexChartData> priceList){
		this.asset		= asset;
		this.priceList	= priceList;
		this.entryList	= highFinder(priceList, TradingSystem.HIGH_LOW);
	}
	
	
	public static List<PoloniexChartData> highFinder(List<PoloniexChartData> priceList, int high){
		boolean open = false;
		BigDecimal currentDay, previousDay;
		int count = 0;
		for(int x = high; x < priceList.size(); x++){
			if(!open){
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
						}if(count >= high){
							entryList.add(priceList.get(x));
							Position position = new Position(asset, priceList, entryList.get(entryList.size() - 1));
							System.out.println(TradingSystem.getCurrencyPair().toString() + 
							" " + DateUtils.toUTCString(priceList.get(x).getDate()) + " " + currentDay
							+ " is at a " + count + " day high!");
							
							open = true;
							break;
						}
					}
				}
			}else{
					//int nextStart = Position.closeLongFinder(priceList,priceList.get(x - 1).getClose(),x, high);
					//x+= nextStart;
					open = false;
				}
			}
		return priceList;
		}
	}
