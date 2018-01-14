package vault;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import asset.Asset;
import javafx.concurrent.Task;
import market.DigitalMarket;
import market.ExchangesEnum;
import market.MarketInterface;
import market.MarketsEnum;
import price.PriceData;
import util.SaveToFile;
import vault.preloader.PreloaderControl;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;

public class Config {
	
	private static boolean isConnected;
	
	private static MarketInterface market;
	
	private static BigDecimal accountBalance = new BigDecimal(0.00);
	
	private static BigDecimal risk;
	
	private static int maxUnits;	

	private static BigDecimal stopLength;
	
	private static int timeFrameDays;
	
	private static int entrySignalDays;
	
	private static int sellSignalDays;

	private static BigDecimal minVolume;

	private static boolean longOnly;
	
	private static boolean sortVol;
	
	private static boolean filterAssets;
	
	private static String[] assetFilter = {"BTC/USDT", "ETH/USDT", "LTC/USDT", "BCH/USDT"};
	
	private static MarketsEnum startMarket = MarketsEnum.BITCOIN;
	
	private static ExchangesEnum ExchangeEnum = ExchangesEnum.POLONIEX;
	
	public static final String defaultDollar = "20000";
	
	public static final String defaultBTC	= "2";
	
	public static final String defaultETH	= "10";
	
	public static boolean firstFlag;
	
	public static Exchange exchange;
	
	public static ExchangeSpecification exchangeSpecification;
	
	//2 systems, 1 my trend following. another -20% buy...sell half double, the Brian method (rational investor)

	public static void ConfigSetUp(){
		PreloaderControl.updateStatus("Checking connection...");
		setConnected();
		PreloaderControl.updateStatus("Connection: " + isConnected());
		setMarket(startMarket);
		PreloaderControl.updateStatus(getMarket().getMarketName() + " loaded!");
		setAccountBalance(2);
		setRisk(1);
		setMaxUnits(5);
		setStopLength(2);
		setTimeFrameDays(99);
		setEntrySignalDays(55);
		setSellSignalDays(11);
		setLongOnly(false);
		setSortVol(true);
		setFilterAssets(false);
		setMinVolume(0);
	}
	
	//for utility purposes
	public static void TestConfig(){
		isConnected = false;
		setMarket(startMarket);
		setAccountBalance((4));
		setRisk(1);
		setMaxUnits(6);
		setStopLength(2);
		setTimeFrameDays(50);
		setEntrySignalDays(25);
		setSellSignalDays(11);
		setLongOnly(true);
		setSortVol(true);
		setFilterAssets(false);
		setMinVolume(0);
	}

	public static MarketInterface getMarket() {
		return market;
	}

	public static void setMarket(MarketsEnum marketsEnum) {
		Config.market = new DigitalMarket(marketsEnum);
		
		//populate new offline txt files
		if(Config.isConnected()){
			Task<Void> task = new Task<Void>() {
	    	    @Override public Void call() {
	    			for(Asset asset : Config.market.getAssetList()){
	    				List<String> saveToFileList = new ArrayList<>();
	    				for(PriceData priceData : asset.getPriceDataList()){
	    					saveToFileList.add(priceData.toString());
	    				}
    					PreloaderControl.updateStatus("Saving latest " + asset.getAssetName());
	    				SaveToFile.writeAssetPriceListToFile(asset, saveToFileList);
	    			}
	    	        return null;
	    	    }
	    	};
	    	new Thread(task).start();
	    }
	}

	public static BigDecimal getRisk() {
		return risk;
	}

	public static void setRisk(double d) {
		Config.risk = new BigDecimal(d);
	}

	public static int getMaxUnits() {
		return maxUnits;
	}

	public static void setMaxUnits(int maxUnits) {
		Config.maxUnits = maxUnits;
	}

	public static BigDecimal getStopLength() {
		return stopLength;
	}

	public static void setStopLength(double stopLength) {
		Config.stopLength = new BigDecimal(stopLength);
	}

	public static int getTimeFrameDays() {
		return timeFrameDays;
	}

	public static void setTimeFrameDays(int timeFrameDays) {
		Config.timeFrameDays = timeFrameDays;
	}

	public static int getEntrySignalDays() {
		return entrySignalDays;
	}

	public static void setEntrySignalDays(int entrySignalDays) {
		Config.entrySignalDays = entrySignalDays;
	}

	public static int getSellSignalDays() {
		return sellSignalDays;
	}

	public static void setSellSignalDays(int sellSignalDays) {
		Config.sellSignalDays = sellSignalDays;
	}

	public static boolean isLongOnly() {
		return longOnly;
	}

	public static void setLongOnly(boolean longOnly) {
		Config.longOnly = longOnly;
	}

	public static boolean isSortVol() {
		return sortVol;
	}

	public static void setSortVol(boolean sortVol) {
		Config.sortVol = sortVol;
	}

	public static BigDecimal getAccountBalance() {
		return accountBalance;
	}

	public static void setAccountBalance(double balance) {
		Config.accountBalance = new BigDecimal(balance);
	}

	public static boolean isConnected() {
		return isConnected;
	}

	public static void setConnected() {
		final Runnable stuffToDo = new Thread() {
			  @Override 
			  public void run() { 
			    /* Do stuff here. */
				try {
		    	    URL myURL = new URL("http://poloniex.com/");
		    	    URLConnection myURLConnection = myURL.openConnection();
		    	    myURLConnection.connect();
		    	    isConnected = true;
		    	} 
		    	catch (IOException e) { 
		    	    
		    	}
			  }
			};

			final ExecutorService executor = Executors.newSingleThreadExecutor();
			final Future<?> future = executor.submit(stuffToDo);
			executor.shutdown(); // This does not cancel the already-scheduled task.

			try { 
			  future.get(2, TimeUnit.SECONDS); 
			}
			catch (InterruptedException ie) { 
			  /* Handle the interruption. Or ignore it. */
				isConnected = false;
			}
			catch (ExecutionException ee) { 
			  /* Handle the error. Or ignore it. */
				isConnected = false;
			}
			catch (TimeoutException te) { 
			  /* Handle the timeout. Or ignore it. */ 
				isConnected = false;
			}
			if (!executor.isTerminated())
			    executor.shutdownNow(); // If you want to stop the code that hasn't finished.
	}

	public static boolean isFilterAssets() {
		return filterAssets;
	}
	
	public static void setFilterAssets(boolean filterAssets){
		Config.filterAssets = filterAssets;
	}

	public static String[] getAssetFilter() {
		return assetFilter;
	}

	public static void setAssetFilter(String[] assetFilter) {
		Config.assetFilter = assetFilter;
	}

	public static int getPriceHistoryYears(){
		return 2;
	}
	
	public static int getMovingAvg(){
		return 20;
	}

	public static BigDecimal getMinVolume() {
		return minVolume;
	}
	
	public static void setMinVolume(int minVolume) {
		Config.minVolume = new BigDecimal(minVolume);
	}
	
	public static MarketsEnum getStartMarket(){
		return startMarket;
	}

	public static ExchangesEnum getExchangeEnum() {
		return ExchangeEnum;
	}

	public static void setExchangeEnum(ExchangesEnum exchangeEnum) {
		Config.ExchangeEnum = exchangeEnum;
	}

}
