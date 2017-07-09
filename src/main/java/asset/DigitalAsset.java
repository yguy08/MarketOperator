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

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;
import org.knowm.xchange.poloniex.service.PoloniexChartDataPeriodType;
import org.knowm.xchange.poloniex.service.PoloniexMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;

import market.Market;
import price.PoloniexPriceList;
import price.PriceData;
import speculator.Speculator;
import trade.Entry;
import trade.Exit;
import util.DateUtils;
import vault.Config;

public class DigitalAsset implements Asset {

	private MarketDataService dataService;
	
	private String assetName;
	
	private List<PoloniexChartData> priceList = new ArrayList<>();
	
	private List<PoloniexChartData> priceSubList;
	
	private List<PriceData> priceDataList = new ArrayList<>();
	
	//static factory method to create online asset
	public static DigitalAsset createOnlineDigitalAsset(Market market, String assetName){
		DigitalAsset digitalAsset = new DigitalAsset();
		digitalAsset.setAssetName(assetName);
		digitalAsset.setMarketDataService(market);
		digitalAsset.setPriceList();
		return digitalAsset;
	}
	
	//static factory method to create offline asset
	public static DigitalAsset createOfflineDigitalAsset(String assetName){
		DigitalAsset digitalAsset = new DigitalAsset();
		digitalAsset.setAssetName(assetName);
		digitalAsset.setOfflinePriceList();
		return digitalAsset;
	}

	@Override
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	@Override
	public void setPriceList() {
		long date = new Date().getTime() / 1000;
		CurrencyPair currencyPair = new CurrencyPair(assetName);
		try {
			priceList = Arrays
					.asList(((PoloniexMarketDataServiceRaw) dataService)
					.getPoloniexChartData(currencyPair, date - 365 * Config.getPriceHistoryYears() * 24 * 60 * 60,
					date, PoloniexChartDataPeriodType.PERIOD_86400));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setOfflinePriceList() {
		List<String> pcd = new ArrayList<>();
		String fileName = getAssetName().replace("/", "") + ".txt";
		URL resourceUrl = getClass().getResource(fileName);
		PoloniexChartData pl;
		try {
			pcd = Files.readAllLines(Paths.get(resourceUrl.toURI()));
			for(String p : pcd){
				String[] arr = p.split(",");
				pl = PoloniexPriceList.createPoloOfflinePriceList(arr);
				priceList.add(pl);
			}
		} catch (IOException | URISyntaxException | ParseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<PoloniexChartData> getPriceList() {
		return priceList;
	}
	
	@Override
	public void setPriceSubList(int start, int end) {
		priceSubList = priceList.subList(start, end + 1);
	}

	@Override
	public String getAssetName() {
		return assetName;
	}

	@Override
	public void setMarketDataService(Market market) {
		dataService = market.getExchange().getMarketDataService();
	}
	
	@Override
	public int getIndexOfLastRecordInSubList() {
		return priceList.indexOf(priceSubList.get(priceSubList.size()-1));
	}
	
	@Override
	public int getIndexOfLastRecordInPriceList() {
		return priceList.size() - 1;
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
		return priceList.get(index).getClose();
	}
	
	@Override
	public BigDecimal getHighPriceFromIndex(int index) {
		return priceList.get(index).getHigh();
	}

	@Override
	public BigDecimal getLowPriceFromIndex(int index) {
		return priceList.get(index).getLow();
	}
	
	@Override
	public Date getDateTimeFromIndex(int index) {
		String date = DateUtils.dateToSimpleDateFormat(priceList.get(index).getDate());
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
		return priceList.get(index).getVolume();
	}

	@Override
	public List<Entry> getEntryList(Speculator speculator) {
		Entry entry;
		List<Entry> entryList = new ArrayList<>();
		int i = getStartIndex(Config.getEntrySignalDays(), Config.getTimeFrameDays()); 
		for(int x = i;x < getPriceList().size();x++){
			setPriceSubList(x - Config.getEntrySignalDays(), x);
			entry = new Entry(this, speculator);
			if(entry.isEntry()){
				entryList.add(entry);
			}				
		}
		return entryList;
	}

	@Override
	public List<Exit> getExitList(Speculator speculator) {
		Exit exit;
		List<Exit> exitList = new ArrayList<>();
		for(Entry e : getEntryList(speculator)){
			for(int i = e.getEntryIndex();i < priceList.size();i++){
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
			for(int i = e.getEntryIndex(); i < priceList.size();i++){
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
		return priceList.get(priceList.size()-1).getDate();
	}

	
	private BigDecimal getLatestPrice() {
		return priceList.get(priceList.size()-1).getClose();
	}

	@Override
	public int getStartIndex(int signalDays, int timeFrameDays) {
		int movingAvg = Config.getMovingAvg();
		if(getIndexOfLastRecordInPriceList() > signalDays + movingAvg + timeFrameDays){
			return getIndexOfLastRecordInPriceList() - (movingAvg +
					timeFrameDays);
		}
		boolean isShortHistory = (priceList.size() < signalDays || priceList.size() < movingAvg);
		if(isShortHistory){
			return priceList.size();
		}else{
			return 0 + signalDays; 
		}
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(DateUtils.dateToMMddFormat(getLatestDate()) + " ");
		sb.append(" $" + getAssetName());
		sb.append(" @" + PriceData.prettyPrice(getLatestPrice()));
		sb.append(" Max: " + PriceData.prettyPrice(getHighForExitFlag()));
		sb.append(" Min: " + PriceData.prettyPrice(getLowForExitFlag()));
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
	public void setPriceDataList() {
		PriceData priceData;
		for(int i = 0; i < priceList.size();i++){
			PoloniexChartData rawData = priceList.get(i);
			priceData = new PriceData(rawData.getDate(), rawData.getHigh(), rawData.getLow(), rawData.getOpen(), rawData.getClose(), rawData.getVolume());
			priceDataList.add(priceData);
		}		
		PriceData.setTrueRange(priceDataList);
	}

	@Override
	public List<PriceData> getPriceDataList() {
		return priceDataList;
	}

}
