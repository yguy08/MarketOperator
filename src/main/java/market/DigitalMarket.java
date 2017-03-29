package market;

import java.util.ArrayList;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.poloniex.PoloniexExchange;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class DigitalMarket implements Market {
	
	List<String> assets = new ArrayList<>();
	
	Exchange exchange;
	ExchangeFactory exchangeFactory;
	
	MarketDataService dataService;
	
	public DigitalMarket(){
		setExchange();
		setAssets();
		setDataService();
	}
	
	public void setDataService(){
		this.dataService = exchange.getMarketDataService();
	}
	
	public MarketDataService getDataService(){
		return this.dataService;
	}

	@Override
	public String getName() {
		return DIGITAL_MARKET;
	}

	@Override
	public void setAssets() {
		List<CurrencyPair> currencyPair = exchange.getExchangeSymbols();
		for(CurrencyPair pair : currencyPair){
			assets.add(pair.toString());
		}
	}

	@Override
	public List<String> getAssets() {
		return this.assets;
	}

	@Override
	public void setExchange() {
		this.exchange = ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName());
	}

	@Override
	public String getExchangeName() {
		return exchange.getExchangeSpecification().toString();
	}

	@Override
	public String getSingleAsset(String assetName) {
		CurrencyPair currencyPair = new CurrencyPair(assetName);
		return currencyPair.toString();
	}
	
	
	
	

}
