package operator;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
	
	public static final Exchange poloniex 				= ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName());
	public static final MarketDataService dataService 	= poloniex.getMarketDataService();;
	public static final List<CurrencyPair> marketList 	= poloniex.getExchangeSymbols();
	
	static List<PoloniexChartData> priceList;
	
	public static final int HIGH_LOW = 20;
	public static final int CLOSE = 10;
	public static final BigDecimal RISK = new BigDecimal(0.01, MathContext.DECIMAL32);
	public static final int ACCOUNT_SIZE = 3;
	public static final BigDecimal BIGDECIMAL_ACCOUNT_SIZE = new BigDecimal(TradingSystem.ACCOUNT_SIZE, MathContext.DECIMAL32);
    
    public static List<PoloniexChartData> setCustomPriceList(PoloniexMarketDataServiceRaw dataService, String currencyPairStr, Long dateFrom) throws IOException{
    	long dateTo = new Date().getTime() / 1000;
    	List<PoloniexChartData> myList;
    	CurrencyPair currencyPair = new CurrencyPair(currencyPairStr);
    	myList = Arrays.asList(dataService.getPoloniexChartData
				(currencyPair, dateFrom, dateTo, PoloniexChartDataPeriodType.PERIOD_86400));
    	return myList;
    }
    
    /*
    public static void marketsToWatch() throws Exception{
    	String assetName;
    	long dateFrom = new Date().getTime() / 1000 - (HIGH_LOW * 24 * 60 * 60);
    	long longerDate = new Date().getTime() / 1000 - (HIGH_LOW * 2 * 24 * 60 * 60);
		
		for(int x = 0; x < marketList.size();x++){
		
			assetName = marketList.get(x).toString();
			
			priceList = setCustomPriceList((PoloniexMarketDataServiceRaw) dataService, assetName, dateFrom);
		
			Asset asset = new Asset(assetName, priceList);
			System.out.println(("Current Price: " + asset.getPrice()));
		
			//start with current price -> go backwards
			Entry entry = new Entry(asset.getName(), asset.getPriceList());
	
			if(entry.entryList.size() > 0){
				System.out.println("***** MARKET TO WATCH *****");
				System.out.println("***** " + asset.getName());
				System.out.println("***** " + HIGH_LOW + " day high");
				System.out.println("***** " + "Date: " + entry.entryList.get(0).getDate());
				System.out.println("***** ENTRY STATS *****");
				priceList = setCustomPriceList((PoloniexMarketDataServiceRaw) dataService, assetName, longerDate);
				Position position = new Position(assetName, priceList);
				System.out.println("ATR: " + position.trueRange);
				System.out.println("Buy Amount: " + position.entrySize + " For Total: " + position.entrySize.multiply(entry.entryList.get(0).getClose()));
				System.out.println("***** END ****");
			}else{
				System.out.println("Not at a high, skip...");
			}
		}
	}*/
 
}
