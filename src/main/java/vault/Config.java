package vault;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;

import market.Market;
import market.MarketFactory;
import market.MarketsEnum;

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

	private static boolean longOnly;
	
	private static boolean sortVol;
	
	public static void ConfigSetUp(){
		setConnected();
		setMarket(MarketsEnum.BITCOIN);
		setAccountBalance(new BigDecimal(4));
		setAccountBalance(new BigDecimal(4));
		setRisk(new BigDecimal(1));
		setMaxUnits(6);
		setStopLength(new BigDecimal(2));
		setTimeFrameDays(50);
		setEntrySignalDays(25);
		setSellSignalDays(11);
		setLongOnly(true);
		setSortVol(true);
	}
	
	//for utility purposes
	public static void TestConfig(){
		isConnected = false;
		setMarket(MarketsEnum.BITCOIN);
		setAccountBalance(new BigDecimal(4));
		setAccountBalance(new BigDecimal(4));
		setRisk(new BigDecimal(1));
		setMaxUnits(6);
		setStopLength(new BigDecimal(2));
		setTimeFrameDays(50);
		setEntrySignalDays(25);
		setSellSignalDays(11);
		setLongOnly(true);
		setSortVol(true);		
	}

	public static Market getMarket() {
		return market;
	}

	public static void setMarket(MarketsEnum marketsEnum) {
		Config.market = MarketFactory.createMarket(marketsEnum);
	}

	public static BigDecimal getRisk() {
		return risk;
	}

	public static void setRisk(BigDecimal risk) {
		Config.risk = risk;
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

	public static void setStopLength(BigDecimal stopLength) {
		Config.stopLength = stopLength;
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

	public static void setAccountBalance(BigDecimal accountBalance) {
		Config.accountBalance = accountBalance;
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

}
