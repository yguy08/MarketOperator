package market;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.knowm.xchange.service.marketdata.MarketDataService;

import fileparser.FileParser;

public class StockMarket implements Market {
	
	List<String> assets = new ArrayList<>();
		
	public StockMarket(){
		setAssets();
		setExchange();
	}
	
	@Override
	public String getName() {
		return STOCK_MARKET;
	}

	@Override
	public void setAssets() {
		List<String> listFromFile;
		try {
			listFromFile = FileParser.readStockTickerList();
			for(int z = 0; z < listFromFile.size(); z++){
				String[] split = listFromFile.get(z).split(",");
				assets.add(split[0]);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	@Override
	public List<String> getAssets() {
		return this.assets;
	}

	@Override
	public void setExchange() {
		
	}

	@Override
	public String getExchangeName() {
		return null;
	}

	@Override
	public String getSingleAsset(String assetName) {
		String singleAsset = assets.get(assets.indexOf(assetName));
		return singleAsset;
	}

	@Override
	public void setDataService() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MarketDataService getDataService() {
		// TODO Auto-generated method stub
		return null;
	}

}
