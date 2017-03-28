package market;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import exchange.Exchange;
import exchange.ExchangeFactory;
import fileparser.FileParser;

public class StockMarket implements Market {
	
	List<String> assets = new ArrayList<>();
	
	Exchange exchange;
	ExchangeFactory exchangeFactory;
	
	public StockMarket(){
		try {
			setAssets();
			setExchange();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String getName() {
		return STOCK_MARKET;
	}

	@Override
	public void setAssets() throws IOException {
		List<String> listFromFile = FileParser.readStockTickerList();
		for(int z = 0; z < listFromFile.size(); z++){
			String[] split = listFromFile.get(z).split(",");
			assets.add(split[0]);
		}
	}

	@Override
	public List<String> getAssets() {
		return this.assets;
	}

	@Override
	public void setExchange() {
		exchangeFactory = new ExchangeFactory();
		exchange = exchangeFactory.createExchange(STOCK_MARKET);
	}

	@Override
	public String getExchangeName() {
		return exchange.getName();
	}

	@Override
	public String getSingleAsset(String assetName) {
		String singleAsset = assets.get(assets.indexOf(assetName));
		return singleAsset;
	}

}
