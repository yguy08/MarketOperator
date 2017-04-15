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

import market.DigitalMarket;
import market.Market;
import speculate.Speculate;

public class DigitalAsset implements Asset {
	
	public static final MarketDataService dataService 	= DigitalMarket.exchange.getMarketDataService();
	
	String marketName;
	String assetName;
	
	List<PoloniexChartData> priceList = new ArrayList<>();
	List<BigDecimal> closeList	= new ArrayList<>();
	List<BigDecimal> lowList = new ArrayList<>();
	List<BigDecimal> highList = new ArrayList<>();
	
	
	private List<PoloniexChartData> priceSubList;
	
	List<BigDecimal> closeSubList	= new ArrayList<>();
	
	public DigitalAsset(Market market, String assetName){
		this.marketName = market.getMarketName();
		this.assetName	= assetName;
		setPriceList(this.assetName);
		setCloseList();
		setLowList();
		setHighList();
	}

	@Override
	public void setAsset(String assetName) {
		this.assetName = assetName;
	}

	@Override
	public String getAsset() {
		return this.assetName;
	}

	@Override
	public void setPriceList(String assetName) {
		long date = new Date().getTime() / 1000;
		CurrencyPair currencyPair = new CurrencyPair(assetName);
		try {
			this.priceList = Arrays
					.asList(((PoloniexMarketDataServiceRaw) dataService)
					.getPoloniexChartData(currencyPair, date - Speculate.DAYS * 24 * 60 * 60,
					date, PoloniexChartDataPeriodType.PERIOD_86400));
		} catch (IOException e) {
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
	public String toString(){
		return this.marketName + ": [ " + this.assetName + " ] " + this.priceList;   
	}

}
