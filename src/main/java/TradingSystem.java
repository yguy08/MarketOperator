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
		
		String m = "XMR/BTC";
		
		setPriceList((PoloniexMarketDataServiceRaw) dataService, m);
		
		TimeZone timeZone = TimeZone.getTimeZone("UTC");
		Calendar calendar = Calendar.getInstance(timeZone);
		calendar.set(2017, 2, 01, 0, 0, 0);
		
		Asset.setPriceList(poloniexChartData);
		
		Entry entry = new Entry(m, getPriceList(), calendar.getTime());
		
		for(int x = 0; x < Entry.entries.size();x++){
			System.out.println(Entry.entries.get(x));
		}
		
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
    
    
    
    

}
