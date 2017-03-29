package market;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fileparser.FileParser;

public class StockMarket implements Market {
	
	List<String> assetList = new ArrayList<>();
	
	private String marketName = null;
	private String assetName = null;
		
	public StockMarket(){
		setAllAssets();
	}
	
	public StockMarket(String marketName, String assetName) {
		this.marketName = marketName;
		this.assetName = assetName;
		setAllAssets();
	}

	@Override
	public String getMarketName() {
		return this.marketName;
	}
	
	@Override
	public void setAsset(String assetName) {
		this.assetName = assetName;
	}

	@Override
	public String getAsset() {
		// TODO Auto-generated method stub
		return this.assetName;
	}

	@Override
	public void setAllAssets() {
		List<String> listFromFile;
		try {
			listFromFile = FileParser.readStockTickerList();
			for(int z = 0; z < listFromFile.size(); z++){
				String[] split = listFromFile.get(z).split(",");
				assetList.add(split[0]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}

	@Override
	public <E> List<E> getAllAssets() {
		return (List<E>) this.assetList;
	}

	@Override
	public void setExchange() {
		
	}

	@Override
	public String getExchangeName() {
		return "Robinhood";
	}

	@Override
	public void setDataService() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <E> Object getDataService() {
		// TODO Auto-generated method stub
		return null;
	}

}
