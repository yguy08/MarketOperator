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
	
	static final Exchange poloniex 				= ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName());
	static final MarketDataService dataService 	= poloniex.getMarketDataService();;
	static final List<CurrencyPair> marketList 	= poloniex.getExchangeSymbols();
	
	static List<PoloniexChartData> priceList;
	
	static final int HIGH_LOW = 20;
	static final int CLOSE = 10;
	static final BigDecimal RISK = new BigDecimal(0.01, MathContext.DECIMAL32);
	static final int ACCOUNT_SIZE = 3;
	static final BigDecimal BIGDECIMAL_ACCOUNT_SIZE = new BigDecimal(TradingSystem.ACCOUNT_SIZE, MathContext.DECIMAL32);

	
    public static void main(String[] args) throws Exception {
    	
    	marketsToWatch();
    	
    	//positionBackTest();
    	//marketsToClose();
    	
    }
	
	public static void stockWatchList() throws IOException{
	setStockList("Fb-0512-0317.csv");
	List<StockChartData> stockChartData = new ArrayList<>();

	for(int x = 0; x < getCloseList().size();x++){
		stockChartData.add(new StockChartData(getDateList().get(x), getCloseList().get(x), getHighList().get(x), getLowList().get(x)));
	}

	StockAsset stockAsset = new StockAsset("FB", stockChartData);
	System.out.println("Current Price: " + stockAsset.getPrice());

	StockEntry stockEntry = new StockEntry(stockAsset.getName(), stockAsset.getPriceList());

	if(stockEntry.entryList.get(0).equals(stockAsset.getPriceList().get(0))){
		System.out.println("***** MARKET TO WATCH *****");
		System.out.println("***** " + stockAsset.getName());
		System.out.println("***** " + HIGH_LOW + " day high");
		System.out.println("***** " + "Date: " + stockEntry.entryList.get(0).getDate());
		System.out.println("***** ENTRY STATS *****");
	}else{
		System.out.println("Not currently at a high...");
		System.out.println("Last high was: " + stockEntry.entryList.get(0).getDate() + " "
				+ stockEntry.entryList.get(0).getClose());
	}
	}
	
	public static List<BigDecimal> getCloseList(){
		return closeList;
	}
	
	public static List<BigDecimal> getHighList(){
		return highList;
	}
	
	public static List<BigDecimal> getLowList(){
		return lowList;
	}
	
	public static List<String> getDateList(){
		return dateList;
	}
	
	public static void setStockList(String name) throws IOException{
		
		List<String> myString = FileParser.readYahooStcokFileByLines(name);
		
		for(int z = 0; z < myString.size(); z++){
			List<String> myList = new ArrayList<>();
			String[] split = myString.get(z).split(",");
			for(String x : split){
				myList.add(x);
			}
			
			String trimDate = myList.get(0);
			//System.out.println(trimDate);
			String trimHigh = myList.get(2);
			//System.out.println(trimHigh);
			String trimLow = myList.get(3);
			//System.out.println(trimLow);
			String trimClose = myList.get(4);
			//System.out.println(trimClose);
			
			setHighList(new BigDecimal(trimHigh));
			setLowList(new BigDecimal(trimLow));
			setCloseList(new BigDecimal(trimClose));
			setDateList(trimDate);
			
		}
	}

	public static void setDateList(String date){
		dateList.add(date);
	}
	
	public static void setHighList(BigDecimal bd){
		highList.add(bd);
		
	}
	
	public static void setLowList(BigDecimal bd){
		lowList.add(bd);
	}

	
	public static void setCloseList(BigDecimal bd){
		closeList.add(bd);
	}
    
    public static List<PoloniexChartData> setCustomPriceList(PoloniexMarketDataServiceRaw dataService, String currencyPairStr, Long dateFrom) throws IOException{
    	long dateTo = new Date().getTime() / 1000;
    	List<PoloniexChartData> myList;
    	CurrencyPair currencyPair = new CurrencyPair(currencyPairStr);
    	myList = Arrays.asList(dataService.getPoloniexChartData
				(currencyPair, dateFrom, dateTo, PoloniexChartDataPeriodType.PERIOD_86400));
    	return myList;
    }
    
    public static void marketsToWatch() throws Exception{
    	String assetName;
    	long dateFrom = new Date().getTime() / 1000 - (HIGH_LOW * 24 * 60 * 60);
    	long longerDate = new Date().getTime() / 1000 - (HIGH_LOW * 2 * 24 * 60 * 60);

	    
    	System.out.println("***** ALL Markets *****");
		for(int x = 0; x < marketList.size();x++){
			System.out.println(marketList.get(x));
		}
		
		for(int x = 0; x < marketList.size();x++){
		
			assetName = marketList.get(x).toString();
		
			System.out.println("Market is: " + assetName);
		
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
	}
    	
    }
