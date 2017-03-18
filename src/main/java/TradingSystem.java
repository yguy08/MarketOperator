import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.poloniex.PoloniexExchange;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class TradingSystem implements MenuOptions {
	
	private static Exchange poloniex;
	
	private static List<CurrencyPair> marketList;
	private static List<PoloniexChartData> poloniexChartData;
	
	private static MarketDataService dataService;
	
	static final int HIGH_LOW = 25;
	static final int CLOSE = 10;
	static final int ACCOUNT_SIZE = 10;
	
    public static void main(String[] args) throws Exception {
    	
    	//create Exchange Instance
    	poloniex = ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName());
    	
		//create MarketDataService
		dataService = poloniex.getMarketDataService();
	    
		//set Market list
		marketList = poloniex.getExchangeSymbols();
		
		//Scanner scanner = new Scanner(System.in);
		//System.out.println("Current entry flag is: " + HIGH_LOW);
		//System.out.println("Current close flag is: " + CLOSE);

		//Display markets
		//for(CurrencyPair currencyPair : marketList){
			//System.out.println(currencyPair);
		//}		
		
		Entry entry = new Entry("AMP/BTC");
		
		System.out.println(entry.getPriceList().get(0));
		System.out.println(entry.getPriceList().get(entry.priceList.size() - 1));
		
		System.out.println(entry.getEntryList().get(0));
		System.out.println(entry.getEntryList().get(entry.entryList.size() - 1));
		
		//for(PoloniexChartData chartData : entry.entryList){
		//System.out.println(Entry.entryList.get(entry.entryList.indexOf(chartData)));
		//}
		
		
		
		/*
		System.out.println("Choose Market: ");
		String market = scanner.nextLine();
		
		switch(market.toUpperCase()){
		case ALL:
			for(CurrencyPair currencyPair : marketList){
					setCurrencyPair(currencyPair.toString());
					startBackTest((PoloniexMarketDataServiceRaw) dataService);
					SystemCalculations.highFinder(poloniexChartData, HIGH_LOW);
			}
				System.out.println(SystemCalculations.getProfitLoss());
				break;
		case BTC_ONLY:
			for(CurrencyPair currencyPair : marketList){
				if(currencyPair.toString().contains("BTC") == true){
					setCurrencyPair(currencyPair.toString());
					startBackTest((PoloniexMarketDataServiceRaw) dataService);
					SystemCalculations.highFinder(poloniexChartData, HIGH_LOW);
				}
			}
				System.out.println(SystemCalculations.getProfitLoss());
				break;
		case VOLUME:
			for(CurrencyPair currencyPair: marketList){
				setCurrencyPair(currencyPair.toString());
				startBackTest((PoloniexMarketDataServiceRaw) dataService);
				SystemCalculations.getWeightedAvg(poloniexChartData);
			}
				break;
		default:
			setCurrencyPair(market);
			startBackTest((PoloniexMarketDataServiceRaw) dataService);
			SystemCalculations.highFinder(poloniexChartData, HIGH_LOW);
			System.out.println(SystemCalculations.getProfitLoss());
		}*/

	}

    public static MarketDataService getDataService(){
    	return dataService;
    }	

}
