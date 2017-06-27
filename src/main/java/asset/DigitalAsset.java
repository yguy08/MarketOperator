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
import java.util.Date;
import java.util.List;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;
import org.knowm.xchange.poloniex.service.PoloniexChartDataPeriodType;
import org.knowm.xchange.poloniex.service.PoloniexMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;

import market.Market;
import trade.Entry;
import trade.Exit;
import price.PoloniexPriceList;
import price.PriceData;
import speculator.Speculator;
import util.DateUtils;
import util.SaveToFile;

public class DigitalAsset implements Asset {
	
	private Market market;

	private MarketDataService dataService;
	
	private String assetName;
	
	private List<PoloniexChartData> priceList = new ArrayList<>();
	
	private List<PoloniexChartData> priceSubList;
	
	//static factory method to create online asset
	public static DigitalAsset createOnlineDigitalAsset(Market market, String assetName){
		DigitalAsset digitalAsset = new DigitalAsset();
		digitalAsset.setMarket(market);
		digitalAsset.setAssetName(assetName);
		digitalAsset.setMarketDataService(market);
		digitalAsset.setPriceList();
		return digitalAsset;
	}
	
	//static factory method to create offline asset
	public static DigitalAsset createOfflineDigitalAsset(Market market, String assetName){
		DigitalAsset digitalAsset = new DigitalAsset();
		digitalAsset.setMarket(market);
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
		//consider updating date range to something more configurable
		long date = new Date().getTime() / 1000;
		CurrencyPair currencyPair = new CurrencyPair(assetName);
		try {
			priceList = Arrays
					.asList(((PoloniexMarketDataServiceRaw) dataService)
					.getPoloniexChartData(currencyPair, date - 365 * Speculator.getPriceHistoryYears() * 24 * 60 * 60,
					date, PoloniexChartDataPeriodType.PERIOD_86400));
			PoloniexPriceList pl;
			List<String> plist = new ArrayList<>();
			for(PoloniexChartData p : priceList){
				pl = new PoloniexPriceList(p.getDate(), p.getHigh(), p.getLow(), p.getOpen(), p.getClose(), p.getVolume(), p.getQuoteVolume(), p.getWeightedAverage());
				plist.add(pl.toString());
			}
			SaveToFile.writeAssetPriceListToFile(this, plist);
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
		int i = getStartIndex(speculator.getEntrySignalDays(), speculator.getTimeFrameDays()); 
		for(int x = i;x < getPriceList().size();x++){
			setPriceSubList(x - speculator.getEntrySignalDays(), x);
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
				setPriceSubList(i - speculator.getSellSignalDays(), i);
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
				setPriceSubList(i - speculator.getSellSignalDays(), i);
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
		if(getIndexOfLastRecordInPriceList() > signalDays + Speculator.getMovingAvg() + timeFrameDays){
			return getIndexOfLastRecordInPriceList() - (Speculator.getMovingAvg() +
					timeFrameDays);
		}
		boolean isShortHistory = (priceList.size() < signalDays || priceList.size() < Speculator.getMovingAvg());
		if(isShortHistory){
			return priceList.size();
		}else{
			return 0 + signalDays; 
		}
	}

	@Override
	public void setMarket(Market market) {
		this.market = market;
	}

	@Override
	public Market getMarket() {
		return market;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(DateUtils.dateToMMddFormat(getLatestDate()) + " ");
		sb.append(" $" + getAssetName());
		sb.append(" @" + PriceData.prettyPrice(getLatestPrice()));
		sb.append(" Max: " + PriceData.prettyPrice(getHighForExitFlag(new DigitalSpeculator())));
		sb.append(" Min: " + PriceData.prettyPrice(getLowForExitFlag(new DigitalSpeculator())));
		return sb.toString();   
	}

	@Override
	public BigDecimal getHighForExitFlag(Speculator speculator) {
		setPriceSubList(getIndexOfLastRecordInPriceList() - speculator.getSellSignalDays(), getIndexOfLastRecordInPriceList());
		BigDecimal maxPrice = Collections.max(getClosePriceListFromSubList());
		return maxPrice;
	}

	@Override
	public BigDecimal getLowForExitFlag(Speculator speculator) {
		setPriceSubList(getIndexOfLastRecordInPriceList() - speculator.getSellSignalDays(), getIndexOfLastRecordInPriceList());
		BigDecimal minPrice = Collections.min(getClosePriceListFromSubList());
		return minPrice;
	}

}
