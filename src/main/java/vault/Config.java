package vault;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;

import asset.Asset;
import javafx.concurrent.Task;
import market.Market;
import market.MarketFactory;
import market.MarketsEnum;
import price.PriceData;
import util.SaveToFile;
import vault.preloader.PreloaderControl;

public class Config {
	
	private static boolean isConnected;
	
	private static Market market;
	
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
	
	private static String[] assetFilter = {"XMR", "ETH", "DASH", "XRP", "ETC", "DCR","LTC","FCT"};
	
	private static MarketsEnum startMarket = MarketsEnum.BITCOIN;
	
	//2 systems, 1 my trend following. another -20% buy...sell half double, the Brian method (rational investor)
	
	public static void ConfigSetUp(){
		PreloaderControl.updateStatus("Checking connection...");
		setConnected();
		PreloaderControl.updateStatus("Connection: " + isConnected());
		setMarket(startMarket);
		PreloaderControl.updateStatus(getMarket().getMarketName() + " loaded!");
		setAccountBalance(4);
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

	public static Market getMarket() {
		return market;
	}

	public static void setMarket(MarketsEnum marketsEnum) {
		Config.market = MarketFactory.createMarket(marketsEnum);
		
		//populate new offline txt files
		if(Config.isConnected()){
			Task<Void> task = new Task<Void>() {
	    	    @Override public Void call() {
	    			for(Asset asset : Config.market.getAssetList()){
	    				List<String> saveToFileList = new ArrayList<>();
	    				for(PoloniexChartData p : asset.getPriceList()){
	    					saveToFileList.add(new PriceData(p.getDate(), p.getHigh(), p.getLow(), p.getOpen(), p.getClose(), p.getVolume()).toString());
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
		try {
    	    URL myURL = new URL("http://poloniex.com/");
    	    URLConnection myURLConnection = myURL.openConnection();
    	    myURLConnection.connect();
    	    isConnected = true;
    	} 
    	catch (IOException e) { 
    	    isConnected = false;
    	}
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

}
