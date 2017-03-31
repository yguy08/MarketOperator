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

public class DigitalAsset implements Asset {
	
	public static final MarketDataService dataService 	= DigitalMarket.exchange.getMarketDataService();
	
	String marketName;
	String assetName;
	
	List<PoloniexChartData> priceList = new ArrayList<>();
	
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
			priceList = Arrays.asList(((PoloniexMarketDataServiceRaw) dataService).getPoloniexChartData(currencyPair, date - 365 * 24 * 60 * 60,
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
	public String toString(){
		return this.marketName + ": [ " + this.assetName + " ] " + this.priceList;   
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
	public void setPriceSubList(List priceList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<?> getPriceSubList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCloseSubList() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List getCloseSubList() {
		// TODO Auto-generated method stub
		return null;
	}

}
