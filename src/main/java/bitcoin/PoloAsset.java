package bitcoin;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;
import org.knowm.xchange.poloniex.service.PoloniexChartDataPeriodType;
import org.knowm.xchange.poloniex.service.PoloniexMarketDataServiceRaw;

import operator.TradingSystem;

public class PoloAsset {
	
	public String name;
	public List<PoloniexChartData> priceList;
	public BigDecimal price;
	
	public PoloAsset(String name, List<PoloniexChartData> poloniexChartData){
		this.name 		= name;
		this.price		= poloniexChartData.get((poloniexChartData.size() - 1)).getClose();
		this.priceList 	= poloniexChartData;
	}
	
	public PoloAsset(String name, PoloMarket polo) throws IOException{
		this.name = name;
		priceList = entirePriceHistory((PoloniexMarketDataServiceRaw) polo.dataService, name);
	}
	
	public List<PoloniexChartData> entirePriceHistory(PoloniexMarketDataServiceRaw dataService, String name) throws IOException{
		long dateTo = new Date().getTime() / 1000;
    	long dateFrom = new Date().getTime() / 1000 - (365 * 24 * 60 * 60);
    	List<PoloniexChartData> list;
    	CurrencyPair currencyPair = new CurrencyPair(name);
    	list = Arrays.asList(dataService.getPoloniexChartData
				(currencyPair, dateFrom, dateTo, PoloniexChartDataPeriodType.PERIOD_86400));
    	return list;
	}

}
