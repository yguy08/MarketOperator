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

import market.PoloMarket;

public class PoloAsset {
	
	public String name;
	public List<PoloniexChartData> priceList;
	
	public PoloAsset(String name, PoloMarket polo) throws IOException{
		this.name = name;
		priceList = getEntirePriceHistory((PoloniexMarketDataServiceRaw) polo.dataService, name);
	}
	
	public List<BigDecimal> getCloseList(){
		List<BigDecimal> closeList = new ArrayList<>();
		for(int x = 0; x < this.priceList.size(); x++){
			closeList.add(this.priceList.get(x).getClose());
		}
		return closeList;
	}
	
	public List<BigDecimal> getDateList(){
		List<BigDecimal> dateList = new ArrayList<>();
		for(int x = 0; x < this.priceList.size();x++){
			dateList.add(new BigDecimal(this.priceList.get(x).getDate().getTime()));
		}
		//EASYD
		return dateList;
	}
	
	//output close list from a start and end index
	public void printCloseList(int start, int end){
		if(end > this.priceList.size()){
			end = this.priceList.size();
		}else if(start > this.priceList.size()){
			start = 0;
		}
		for(int x = start; x < end; x++){
			System.out.println(this.getCloseList().get(x));
		}
	}
	
	public List<PoloniexChartData> getPriceList(PoloAsset asset){
		return priceList;
	}
	
	
	public List<PoloniexChartData> getEntirePriceHistory(PoloniexMarketDataServiceRaw dataService, String name) throws IOException{
		long dateTo = new Date().getTime() / 1000;
    	long dateFrom = new Date().getTime() / 1000 - (365 * 24 * 60 * 60);
    	List<PoloniexChartData> list;
    	CurrencyPair currencyPair = new CurrencyPair(name);
    	list = Arrays.asList(dataService.getPoloniexChartData
				(currencyPair, dateFrom, dateTo, PoloniexChartDataPeriodType.PERIOD_86400));
    	return list;
	}

}
