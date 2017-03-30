package market;

import java.util.ArrayList;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.poloniex.PoloniexExchange;

public class DigitalMarket implements Market {
	
	public static final Exchange exchange = ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName());
	
	private String marketName = Market.DIGITAL_MARKET;
	
	List<CurrencyPair> assets = new ArrayList<>();
	
	public DigitalMarket(){
		setAssets();
	}
	
	@Override
	public String getMarketName() {
		return this.marketName;
	}
	
	@Override
	public void setAssets() {
		assets = exchange.getExchangeSymbols();
	}
	
	@Override
	public List<CurrencyPair> getAssets() {
		return this.assets;
	}
	
	@Override
	public String toString(){
		return this.marketName + ": " + this.assets;
	}
	

}
