package market;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import exchange.Exchange;
import exchange.ExchangeFactory;

public class DigitalMarket implements Market {
	
	List<String> assets = new ArrayList<>();
	
	Exchange exchange;
	ExchangeFactory exchangeFactory;

	@Override
	public String getName() {
		return DIGITAL_MARKET;
	}

	@Override
	public void setAssets() throws IOException {
		
	}

	@Override
	public List<String> getAssets() {
		return null;
	}

	@Override
	public void setExchange() {
		exchangeFactory = new ExchangeFactory();
		exchange = exchangeFactory.createExchange(DIGITAL_MARKET);
	}

	@Override
	public String getExchangeName() {
		return exchange.getName();
	}

	@Override
	public String getSingleAsset(String assetName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	

}
