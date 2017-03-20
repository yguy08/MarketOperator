import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.poloniex.PoloniexExchange;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;
import org.knowm.xchange.poloniex.service.PoloniexChartDataPeriodType;
import org.knowm.xchange.poloniex.service.PoloniexMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.utils.DateUtils;

/*
 * The development and application of a trading strategy follows eight steps:
 * (1) Formulation, (2) Specification in computer-testable form, (3) Preliminary testing, (4) Optimization, 
 * (5) Evaluation of performance and robustness, (6) Trading of the strategy, (7) Monitoring of trading performance, 
 * (8) Refinement and evolution.
 * 
 *  For every trading strategy one needs to define 
 *  assets to trade, entry/exit points and money management rules. 
 *	Bad money management can make a potentially profitable strategy unprofitable.
 * 
 */

public class TradingSystem {
	
	private static Exchange poloniex;
	
	private static List<CurrencyPair> marketList;
	private static List<PoloniexChartData> poloniexChartData;
	private static List<PoloniexChartData> priceList;
	
	private static MarketDataService dataService;
	
	static final int HIGH_LOW = 20;
	static final int CLOSE = 10;
	static final int ACCOUNT_SIZE = 10;
	
    public static void main(String[] args) throws Exception {
    	
    	//marketsToWatch();
    	positionBackTest();
    	
    	
    	
		
	}

    public static MarketDataService getDataService(){
    	return dataService;
    }
    
    public static void setPriceList(PoloniexMarketDataServiceRaw dataService, String currencyPairStr) throws IOException{
    	long now = new Date().getTime() / 1000;
    	CurrencyPair currencyPair = new CurrencyPair(currencyPairStr);
    	priceList = Arrays.asList(dataService.getPoloniexChartData
				(currencyPair, now - 8760 * 60 * 60, now, PoloniexChartDataPeriodType.PERIOD_86400));
    }
    
    public static List<PoloniexChartData> getPriceList(){
    	return priceList;
    }
    
    public static void marketsToWatch() throws Exception{
    	//create Exchange Instance
    	poloniex = ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName());
    	
		//create MarketDataService
		dataService = poloniex.getMarketDataService();
	    
		//set Market list
		marketList = poloniex.getExchangeSymbols();
		
		System.out.println("***** ALL Markets *****");
		for(int x = 0; x < marketList.size();x++){
			System.out.println(marketList.get(x));
		}
		
		for(int x = 0; x < marketList.size();x++){
		String m = marketList.get(x).toString();
		System.out.println("Market is: " + m);
		setPriceList((PoloniexMarketDataServiceRaw) dataService, m);
		
		TimeZone timeZone = TimeZone.getTimeZone("UTC");
		Calendar calendar = Calendar.getInstance(timeZone);
		calendar.set(2017, 2, 15, 0, 0, 0);
		//calendar.set(calendar.getTime().getYear(),calendar.getTime().getMonth(),calendar.getTime().getDay(),0,0,0);
		
		Asset asset = new Asset(m,getPriceList());
		System.out.println(("Price is: " + asset.getPrice()));
		
		Entry entry = new Entry(asset.getName(), Asset.getPriceList(), calendar.getTime());

		if(entry.entryList.size() > 0){
			System.out.println("***** MARKET TO WATCH ****");
			System.out.println("***** " + asset.getName() + " *****");
			System.out.println("Entries for: " + asset.getName() + " since: " + calendar.getTime());
		for(int z = 0; z < entry.entryList.size();z++){
			System.out.println("At a high: " + entry.getName());
			System.out.println("Date: " + DateUtils.toUTCString(entry.entryList.get(z).getDate()));
			System.out.println("Opening up new position for: " + entry.getName() + " @ " + entry.entryList.get(z).getClose());
			Position position = new Position(asset.getName(), Asset.getPriceList(),calendar.getTime(),entry.entryList.get(z));
			System.out.println("Average True Range: " + position.trueRange);
		}
		System.out.println("***** END ****");
		}else{
			System.out.println("Not at a high, skip...");
		}
		}
    	
    }
    
    public static void positionBackTest() throws Exception{
    	//create Exchange Instance
    	poloniex = ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName());
    	
		//create MarketDataService
		dataService = poloniex.getMarketDataService();
	    
		//set Market list
		marketList = poloniex.getExchangeSymbols();
		
		System.out.println("***** Market *****");
		
		String m = "XMR/BTC";
		System.out.println("Market is: " + m);
		setPriceList((PoloniexMarketDataServiceRaw) dataService, m);
		
		TimeZone timeZone = TimeZone.getTimeZone("UTC");
		Calendar calendar = Calendar.getInstance(timeZone);
		calendar.set(2016, 7, 15, 0, 0, 0);
		
		Asset asset = new Asset(m,getPriceList());
		System.out.println(("Price is: " + asset.getPrice()));
		
		Entry entry = new Entry(asset.getName(), Asset.getPriceList(), calendar.getTime());

		if(entry.entryList.size() > 0){
			System.out.println("***** MARKET TO WATCH *****");
			System.out.println("***** " + asset.getName() + " *****");
			System.out.println("Entries for: " + asset.getName() + " since: " + calendar.getTime());
		for(int z = 0; z < entry.entryList.size();z++){
			System.out.println("At a high: " + entry.getName() + " Date: " + DateUtils.toUTCString(entry.entryList.get(z).getDate()));
			System.out.println("Open: " + entry.getName() + " @ " + entry.entryList.get(z).getClose());
			Position position = new Position(asset.getName(), Asset.getPriceList(),calendar.getTime(),entry.entryList.get(z));
			System.out.println("Average True Range: " + position.trueRange);
			if(entry.entryList.get(z).getClose() == position.close.getClose()){
			System.out.println("Still open...add");
			}else{
				System.out.println("Close: " + position.close);
			}
		}
		System.out.println("***** END ****");
		}else{
			System.out.println("Never at a high, skip...");
		}
		}
    	
    }
