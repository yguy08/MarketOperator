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
	
	private String marketName = null;
	private String assetName = null;
	
	Exchange exchange;
	ExchangeFactory exchangeFactory;
	
	MarketDataService dataService;
	
	public DigitalMarket(){
		setExchange();
		setAllAssets();
		setDataService();
	}
	
	public DigitalMarket(String marketName, String assetName) {
		this.marketName = marketName;
		this.assetName = assetName;
		new DigitalMarket();
	}

	@Override
	public String getMarketName() {
		return DIGITAL_MARKET;
	}
	
	@Override
	public void setAllAssets() {
		List<CurrencyPair> currencyPair = exchange.getExchangeSymbols();
		for(CurrencyPair pair : currencyPair){
			assets.add(pair.toString());
		}
	}
	
	@Override
	public <E> List<E> getAllAssets() {
		// TODO Auto-generated method stub
		return (List<E>) this.assets;
	}
	
	@Override
	public String getSingleAsset(String assetName) {
		
	}
	
	public void setDataService(){
		this.dataService = exchange.getMarketDataService();
	}
	
	public <E> Object getDataService(){
		return this.dataService;
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
	public void setAsset(String assetName) {
		this.assetName = assetName;
	}

	@Override
	public String getAsset() {
		CurrencyPair currencyPair = new CurrencyPair(assetName);
		return currencyPair.toString();
	}
	
	
	
	

}
