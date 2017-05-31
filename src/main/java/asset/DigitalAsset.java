package asset;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
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
import price.PoloniexPriceList;
import util.DateUtils;
import util.SaveToFile;
import util.StringFormatter;

public class DigitalAsset implements Asset {
	
	Date date;

	public MarketDataService dataService;
	
	String marketName;
	String assetName;
	
	List<PoloniexChartData> priceList = new ArrayList<>();
	
	List<BigDecimal> closeList	= new ArrayList<>();
	
	List<BigDecimal> lowList = new ArrayList<>();
	
	List<BigDecimal> highList = new ArrayList<>();
	
	private List<PoloniexChartData> priceSubList;
	
	List<BigDecimal> closeSubList	= new ArrayList<>();
	
	//static factory method to create online asset
	public static DigitalAsset createOnlineDigitalAsset(Market market, String assetName){
		DigitalAsset digitalAsset = new DigitalAsset();
		digitalAsset.setMarketName(market.getMarketName());
		digitalAsset.setAssetName(assetName);
		digitalAsset.setMarketDataService(market);
		digitalAsset.setPriceList();
		digitalAsset.setCloseList();
		digitalAsset.setLowList();
		digitalAsset.setHighList();
		return digitalAsset;
	}
	
	//static factory method to create offline asset
	public static DigitalAsset createOfflineDigitalAsset(Market market, String assetName){
		DigitalAsset digitalAsset = new DigitalAsset();
		digitalAsset.setMarketName(market.getMarketName());
		digitalAsset.setAssetName(assetName);
		digitalAsset.setOfflinePriceList();
		digitalAsset.setCloseList();
		digitalAsset.setLowList();
		digitalAsset.setHighList();
		return digitalAsset;
	}

	@Override
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	@Override
	public String getAsset() {
		return this.assetName;
	}

	@Override
	public void setPriceList() {
		//consider updating date range to something more configurable
		long date = new Date().getTime() / 1000;
		CurrencyPair currencyPair = new CurrencyPair(this.assetName);
		try {
			priceList = Arrays
					.asList(((PoloniexMarketDataServiceRaw) dataService)
					.getPoloniexChartData(currencyPair, date - 365 * 10 * 24 * 60 * 60,
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
		String fileName = this.getAssetName().replace("/", "") + ".txt";
		URL resourceUrl = getClass().getResource(fileName);
		PoloniexChartData pl;
		try {
			pcd = Files.readAllLines(Paths.get(resourceUrl.toURI()));
			for(String p : pcd){
				String[] arr = p.split(",");
				pl = PoloniexPriceList.createPoloOfflinePriceList(arr);
				priceList.add(pl);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<PoloniexChartData> getPriceList() {
		return this.priceList;
	}
	
	@Override
	public void setPriceSubList(int start, int end) {
		this.priceSubList = this.priceList.subList(start, end);
	}
	
	@Override
	public List<PoloniexChartData> getPriceSubList() {
		return this.priceSubList;
	}

	@Override
	public void setCloseList() {
		for(int i = 0; i < this.priceList.size();i++){
			this.closeList.add(this.getPriceList().get(i).getClose());
		}
	}

	@Override
	public List<BigDecimal> getCloseList() {
		return this.closeList;
	}

	@Override
	public void setLowList() {
		for(int i = 0; i < this.priceList.size();i++){
			this.lowList.add(this.getPriceList().get(i).getLow());
		}
	}

	@Override
	public List<BigDecimal> getLowList() {
		return this.lowList;
	}

	@Override
	public void setHighList() {
		for(int i = 0; i < this.priceList.size();i++){
			this.highList.add(this.getPriceList().get(i).getHigh());
		}
	}

	@Override
	public List<BigDecimal> getHighList() {
		return this.highList;
	}

	@Override
	public String getAssetName() {
		return this.assetName;
	}

	@Override
	public String getMarketName() {
		return this.marketName;
	}

	@Override
	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	@Override
	public void setMarketDataService(Market market) {
		 dataService = market.getExchange().getMarketDataService();
	}
	
	@Override
	public String toString(){
		return "[$" + getAssetName() + "] " + StringFormatter.bigDecimalToEightString(getPriceList().get(getPriceList().size() -1 ).getClose());   
	}

	@Override
	public void setAssetPriceList() {
		
	}

	@Override
	public List<?> getAssetPriceList() {
		return this.priceList;
	}

	@Override
	public void setAssetPriceSubList(int start, int end) {
		
	}

	@Override
	public List<?> getAssetPriceSubList() {
		return this.priceSubList;
	}

	@Override
	public Date getDate(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getClose(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOfflineAssetPriceList() {
		
	}

	@Override
	public BigDecimal getCurrentPriceFromSubList() {
		return priceSubList.get(priceSubList.size() - 1).getClose();
	}

	@Override
	public String getCurrentDateStringFromSubList() {
		return DateUtils.dateToSimpleDateFormat(priceSubList.get(priceSubList.size() - 1).getDate());
	}

	@Override
	public BigDecimal getCurrentVolumeFromSubList() {
		return priceSubList.get(priceSubList.size() - 1).getVolume();
	}
	
	@Override
	public int getIndexOfCurrentRecordFromSubList() {
		return priceList.indexOf(priceSubList.get(priceSubList.size()-1));
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

}
