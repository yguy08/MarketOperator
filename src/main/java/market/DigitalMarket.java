package market;

import java.util.ArrayList;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.poloniex.PoloniexExchange;

import utils.SaveToFile;

public class DigitalMarket implements Market {
	
	//consider changing to bitcoin market, then have ETH, XMR and other curriencies as markets...
	
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
		List<CurrencyPair> btcOnly = exchange.getExchangeSymbols();
		List<String> btcOnlyList = new ArrayList<>();
		for(CurrencyPair pairs : btcOnly){
			if(pairs.counter.equals(Currency.BTC)){
				this.assets.add(pairs);
				btcOnlyList.add(pairs.toString());
			}
		}
		
		//SaveToFile.writeMarketListToFile(this, btcOnlyList);
		
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
