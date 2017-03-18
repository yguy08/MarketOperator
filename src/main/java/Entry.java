import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;
import org.knowm.xchange.poloniex.service.PoloniexChartDataPeriodType;
import org.knowm.xchange.poloniex.service.PoloniexMarketDataServiceRaw;
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
	
	public Entry(String asset){
		this.asset		= asset;
		try {
			this.priceList	= setChartData((PoloniexMarketDataServiceRaw) TradingSystem.getDataService(), asset);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.entryList	= highFinder(priceList, TradingSystem.HIGH_LOW);
	}
	
	public Entry(){
		
	}
	

    
    private static List<PoloniexChartData> setChartData(PoloniexMarketDataServiceRaw dataService, String currencyPairStr) throws IOException{
    	long now = new Date().getTime() / 1000;
    	CurrencyPair currencyPair = new CurrencyPair(currencyPairStr);
    	priceList = Arrays.asList(dataService.getPoloniexChartData
				(currencyPair, now - 8760 * 60 * 60, now, PoloniexChartDataPeriodType.PERIOD_86400));
		return priceList;
    }
	
	public static List<PoloniexChartData> getPriceList(){
		return priceList;
	}
	
	public static List<PoloniexChartData> getEntryList(){
		return entryList;
		
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
							
							System.out.println(asset + " " + DateUtils.toUTCString(priceList.get(x).getDate()) + " " + currentDay
							+ " is at a " + count + " day high!");
							
							
							System.out.println("Position opened! " +  DateUtils.toUTCString(position.entry.getDate()) + " "
									+ position.trueRange);
							
							
							
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
		return entryList;
		}
	}
