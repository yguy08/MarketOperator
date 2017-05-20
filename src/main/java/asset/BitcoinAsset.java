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
import price.PriceData;
import util.SaveToFile;
import util.StringFormatter;

public class BitcoinAsset implements Asset {
	
	private String assetName;
	
	private List<PriceData> assetPriceList = new ArrayList<>();
	
	private List<PriceData> assetPriceSubList = new ArrayList<>();
	
	private MarketDataService dataService;

	//static factory method to create offline digital market
	public static BitcoinAsset createOfflineBitcoinAsset(String assetName){
		BitcoinAsset bitcoinAsset = new BitcoinAsset();
		bitcoinAsset.setAssetName(assetName);
		bitcoinAsset.setOfflineAssetPriceList();
		return bitcoinAsset;
	}

	@Override
	public void setAssetName(String assetName) {
		this.assetName = assetName;
		
	}

	@Override
	public String getAssetName() {
		return assetName;
	}

	@Override
	public void setAssetPriceList() {
		//consider updating date range to something more configurable
		long date = new Date().getTime() / 1000;
		CurrencyPair currencyPair = new CurrencyPair(this.assetName);
		PriceData priceData;
		
		try {
			List<PoloniexChartData> poloChartData = Arrays
					.asList(((PoloniexMarketDataServiceRaw) dataService)
					.getPoloniexChartData(currencyPair, date - 365 * 10 * 24 * 60 * 60,
					date, PoloniexChartDataPeriodType.PERIOD_86400));
			for(PoloniexChartData poloniexChartData : poloChartData){
				priceData = new PriceData(poloniexChartData.getDate(), poloniexChartData.getHigh(), 
						poloniexChartData.getLow(), poloniexChartData.getOpen(), poloniexChartData.getClose(), poloniexChartData.getVolume());
				assetPriceList.add(priceData);
			}
			SaveToFile.writeAssetPriceListToFile(this, assetPriceList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<PriceData> getAssetPriceList() {
		return assetPriceList;
	}

	@Override
	public void setAssetPriceSubList(int start, int end) {
		assetPriceSubList = assetPriceList.subList(start, end);
	}

	@Override
	public List<PriceData> getAssetPriceSubList() {
		return assetPriceSubList;
	}

	@Override
	public void setOfflineAssetPriceList() {
		List<String> prices = new ArrayList<>();
		String fileName = this.getAssetName().replace("/", "") + ".txt";
		URL resourceUrl = getClass().getResource(fileName);
		PriceData priceData;
		
		try {
			
			prices = Files.readAllLines(Paths.get(resourceUrl.toURI()));
			
			for(String priceDataString : prices){
				String[] pricesArr = priceDataString.split(",");
				priceData = PriceData.createOfflinePriceData(pricesArr);
				assetPriceList.add(priceData);
			}
		
		} catch (IOException | URISyntaxException | ParseException e) {
			e.printStackTrace();
		}
		
	}
	

	@Override
	public BigDecimal getClose(int index) {
		if(assetPriceList!=null){
			return assetPriceList.get(index).getClose();
		}else{
			return null;
		}
	}
	
	@Override
	public Date getDate(int index) {
		if(assetPriceList!=null){
			return assetPriceList.get(index).getDate();
		}else{
			return null;
		}
	}
	
	@Override
	public void setMarketDataService(Market market) {
		dataService = market.getExchange().getMarketDataService();
	}

	@Override
	public String toString(){
		return "[$" + getAssetName() + "] " + StringFormatter.bigDecimalToEightString(getClose(assetPriceList.size() - 1));   
	}

	@Override
	public String getAsset() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCloseList() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<BigDecimal> getCloseList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLowList() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<BigDecimal> getLowList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setHighList() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<BigDecimal> getHighList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOfflinePriceList() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPriceList() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<PoloniexChartData> getPriceList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPriceSubList(int start, int end) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<PoloniexChartData> getPriceSubList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMarketName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMarketName(String marketName) {
		// TODO Auto-generated method stub
		
	}

	
}
