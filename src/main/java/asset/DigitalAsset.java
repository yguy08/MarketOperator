package asset;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;
import org.knowm.xchange.poloniex.service.PoloniexChartDataPeriodType;
import org.knowm.xchange.poloniex.service.PoloniexMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;

import market.ExchangesEnum;
import market.MarketInterface;
import price.PriceData;
import speculator.Speculator;
import trade.Entry;
import trade.Exit;
import util.DateUtils;
import util.StringFormatter;
import vault.Config;

public class DigitalAsset implements Asset {

	private MarketDataService dataService;
	
	private String assetName;
	
	private List<PriceData> priceSubList;
	
	private List<PriceData> priceDataList = new ArrayList<>();
	
	private Currency currency;
	
	public DigitalAsset(MarketInterface market, CurrencyPair currencyPair){
		if(currencyPair == null){
			throw new IllegalArgumentException();
		}
		//
		Currency currency = currencyPair.counter;
		setAssetName(currencyPair.toString());
		setCurrency(currency);
		//
		if(Config.isConnected()){
			setMarketDataService(market);
			setPriceList();
		}else{
			setOfflinePriceList();
		}
		
		PriceData.setTrueRangeList(priceDataList);
		PriceData.setDayHighOrLow(priceDataList);
	}

	@Override
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	@Override
	public void setPriceList() {
		long date = new Date().getTime() / 1000;
		CurrencyPair currencyPair = new CurrencyPair(assetName);
		PriceData priceData;
		//
		if(Config.getExchangeEnum().equals(ExchangesEnum.POLONIEX)){
			try {
				//
				List<PoloniexChartData> priceList = Arrays.asList(((PoloniexMarketDataServiceRaw) dataService)
						.getPoloniexChartData(currencyPair, date - 365 * Config.getPriceHistoryYears() * 24 * 60 * 60,
						date, PoloniexChartDataPeriodType.PERIOD_86400));
				//
				for(PoloniexChartData dayData : priceList){
					priceData = new PriceData(dayData.getDate(),dayData.getHigh(),dayData.getLow(),dayData.getOpen(),dayData.getClose(),dayData.getVolume());
					priceDataList.add(priceData);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			throw new IllegalArgumentException("Exchange not supported.");
		}
	}
	
	@Override
	public void setOfflinePriceList() {
		String fileName = getAssetName().replace("/", "") + ".txt";
		URL resourceUrl = getClass().getResource(fileName);
		PriceData priceData;
		try {
			List<String> priceDataFromFile = Files.readAllLines(Paths.get(resourceUrl.toURI()));
			for(String priceDataStr : priceDataFromFile){
				priceData = new PriceData(priceDataStr.split(","));
				priceDataList.add(priceData);
			}
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setPriceSubList(int start, int end) {
	    if(priceDataList.size() > Config.getEntrySignalDays()){
	        priceSubList = priceDataList.subList(start, end + 1);
	    }else{
		priceSubList = priceDataList.subList(0, priceDataList.size());
	    }
	}

	@Override
	public String getAssetName() {
		return assetName;
	}

	@Override
	public void setMarketDataService(MarketInterface market) {
		dataService = market.getMarketDataService();
	}
	
	@Override
	public int getIndexOfLastRecordInSubList() {
		return priceDataList.indexOf(priceSubList.get(priceSubList.size()-1));
	}
	
	@Override
	public int getIndexOfLastRecordInPriceList() {
		return priceDataList.size() - 1;
	}

	@Override
	public List<BigDecimal> getClosePriceListFromSubList() {
		List<BigDecimal> closePriceList = new ArrayList<>();
		for(int i = 0; i < priceSubList.size(); i++){
			closePriceList.add(priceSubList.get(i).getClose());
		}
		return closePriceList;
	}

	@Override
	public BigDecimal getClosePriceFromIndex(int index) {
		return priceDataList.get(index).getClose();
	}
	
	@Override
	public BigDecimal getHighPriceFromIndex(int index) {
		return priceDataList.get(index).getHigh();
	}

	@Override
	public BigDecimal getLowPriceFromIndex(int index) {
		return priceDataList.get(index).getLow();
	}
	
	@Override
	public Date getDateTimeFromIndex(int index) {
		String date = DateUtils.dateToSimpleDateFormat(priceDataList.get(index).getDate());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date dateTime;
		try {
			dateTime = df.parse(date);
			return DateUtils.dateToUTCMidnight(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public BigDecimal getVolumeFromIndex(int index) {
		return priceDataList.get(index).getVolume();
	}

	@Override
	public List<Entry> getEntryList(Speculator speculator) {
		Entry entry;
		List<Entry> entryList = new ArrayList<>();
		int i = getStartIndex(Config.getEntrySignalDays(), Config.getTimeFrameDays());
		for(int x = i;x < getPriceDataList().size();x++){
			setPriceSubList(x - Config.getEntrySignalDays(), x);
			try{
				entry = new Entry(this, speculator);
				if(entry.isEntry()){
					entryList.add(entry);
				}
			}catch(Exception e){
				
			}				
		}
		return entryList;
	}

	@Override
	public List<Exit> getExitList(Speculator speculator) {
		Exit exit;
		List<Exit> exitList = new ArrayList<>();
		for(Entry e : getEntryList(speculator)){
			for(int i = e.getEntryIndex();i < priceDataList.size();i++){
				setPriceSubList(i - Config.getSellSignalDays(), i);
				exit = new Exit(e, speculator);
				if(exit.isExit()){
					exitList.add(exit);
					break;
				}
			}
		}
		return exitList;
	}
	
	@Override
	public List<Exit> getEntryStatusList(Speculator speculator) {
		List<Entry> entryList = getEntryList(speculator);
		List<Exit> openList = new ArrayList<>();
		Exit exit;
		for(Entry e : entryList){
			for(int i = e.getEntryIndex(); i < priceDataList.size();i++){
				setPriceSubList(i - Config.getSellSignalDays(), i);
				exit = new Exit(e, speculator);
				if(exit.isExit() || exit.isOpen()){
					openList.add(exit);
					break;
				}
			}
		}		
		return openList;
	}
	
	private Date getLatestDate() {
		return priceDataList.get(priceDataList.size()-1).getDate();
	}

	
	private BigDecimal getLatestPrice() {
		return priceDataList.get(priceDataList.size()-1).getClose();
	}

	@Override
	public int getStartIndex(int signalDays, int timeFrameDays) {
		int movingAvg = Config.getMovingAvg();
		if(getIndexOfLastRecordInPriceList() > signalDays + movingAvg + timeFrameDays){
			return getIndexOfLastRecordInPriceList() - (movingAvg +
					timeFrameDays);
		}
		boolean isShortHistory = (priceDataList.size() < signalDays || priceDataList.size() < movingAvg);
		if(isShortHistory){
			return priceDataList.size();
		}else{
			return 0 + signalDays; 
		}
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(DateUtils.dateToMMddFormat(getLatestDate()) + " ");
		sb.append(" $" + getAssetName());
		boolean isUSD = getCurrency().equals(Currency.USD) || getCurrency().toString().equalsIgnoreCase("USDT");
		if(!(isUSD)){
			sb.append(" @" + StringFormatter.prettySatsPrice(getLatestPrice()));
			sb.append(" H: " + StringFormatter.prettySatsPrice(getHighForExitFlag()));
			sb.append(" L: " + StringFormatter.prettySatsPrice(getLowForExitFlag()));
		}else{
			sb.append(" @" + StringFormatter.prettyUSDPrice(getLatestPrice()));
			sb.append(" H: " + StringFormatter.prettyUSDPrice(getHighForExitFlag()));
			sb.append(" L: " + StringFormatter.prettyUSDPrice(getLowForExitFlag()));
		}
		return sb.toString();   
	}

	@Override
	public BigDecimal getHighForExitFlag() {
		setPriceSubList(getIndexOfLastRecordInPriceList() - Config.getEntrySignalDays(), getIndexOfLastRecordInPriceList());
		BigDecimal maxPrice = Collections.max(getClosePriceListFromSubList());
		return maxPrice;
	}

	@Override
	public BigDecimal getLowForExitFlag() {
		setPriceSubList(getIndexOfLastRecordInPriceList() - Config.getSellSignalDays(), getIndexOfLastRecordInPriceList());
		BigDecimal minPrice = Collections.min(getClosePriceListFromSubList());
		return minPrice;
	}

	@Override
	public List<PriceData> getPriceDataList() {
		return priceDataList;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

}
