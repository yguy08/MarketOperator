import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.poloniex.PoloniexExchange;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;
import org.knowm.xchange.poloniex.service.PoloniexChartDataPeriodType;
import org.knowm.xchange.poloniex.service.PoloniexMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class TradingSystem implements MenuOptions {
	
	private static Exchange poloniex;
	private static CurrencyPair currencyPair;
	
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
		System.out.println("Current entry flag is: " + HIGH_LOW);
		System.out.println("Current close flag is: " + CLOSE);

		//Display markets
		for(CurrencyPair currencyPair : marketList){
			System.out.println(currencyPair);
		}
		
		setCurrencyPair("XMR/BTC");
		
		poloniexChartData = setPoloniexChartData((PoloniexMarketDataServiceRaw) dataService);
		
		Entry entry = new Entry(getCurrencyPair().toString(), poloniexChartData);
		
		
		
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
    
    public static CurrencyPair getCurrencyPair(){
    	return currencyPair;
    }
    
    private static void setCurrencyPair(String x){
    	currencyPair = new CurrencyPair(x);
    }
    
    private static List<PoloniexChartData> setPoloniexChartData(PoloniexMarketDataServiceRaw dataService) throws IOException{
    	long now = new Date().getTime() / 1000;
		poloniexChartData = Arrays.asList(dataService.getPoloniexChartData
				(getCurrencyPair(), now - 8760 * 60 * 60, now, PoloniexChartDataPeriodType.PERIOD_86400));
		return poloniexChartData;
    }	

}
