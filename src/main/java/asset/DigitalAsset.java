package asset;

import java.io.IOException;
import java.math.BigDecimal;
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

public class DigitalAsset implements Asset {
	
	private String assetName;
	
	private List<BigDecimal> closeList = new ArrayList<>();
	
	private List<PoloniexChartData> allPriceList = new ArrayList<>();
	
	MarketDataService dataService;
	
	public DigitalAsset(Market market){
		this.dataService = market.getDataService();
	}
	
	@Override
	public String getAsset() {
		return this.assetName;
	}

	@Override
	public void setCloseList() {
		long dateTo = new Date().getTime() / 1000;
		long dateFrom = new Date().getTime() / 1000 - (365 * 24 * 60 * 60);
		List<PoloniexChartData> myList;
		PoloniexMarketDataServiceRaw rawData = (PoloniexMarketDataServiceRaw) dataService;
    	CurrencyPair currencyPair = new CurrencyPair(this.assetName);
    	try {
			myList = Arrays.asList(rawData.getPoloniexChartData(currencyPair, dateFrom, dateTo, PoloniexChartDataPeriodType.PERIOD_86400));
			for(int i = 0; i < myList.size();i++){
				closeList.add(myList.get(i).getClose());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<BigDecimal> getCloseList() {
		return this.closeList;
	}

	@Override
	public void setAsset(String assetName) {
		this.assetName = assetName;
	}

	@Override
	public void setAllPriceList() {
		long dateTo = new Date().getTime() / 1000;
		long dateFrom = dateTo - (365 * 24 * 60 * 60);
		PoloniexMarketDataServiceRaw rawData = (PoloniexMarketDataServiceRaw) dataService;
    	CurrencyPair currencyPair = new CurrencyPair(this.assetName);
    	
    	try {
			allPriceList = Arrays.asList(rawData.getPoloniexChartData(currencyPair, dateFrom, dateTo, PoloniexChartDataPeriodType.PERIOD_86400));
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public List<PoloniexChartData> getAllPriceList() {
		return allPriceList;
	}

}
