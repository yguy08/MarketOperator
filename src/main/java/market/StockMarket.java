package market;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import util.FileParser;

public class StockMarket implements Market {
	
	private String marketName = Market.STOCK_MARKET;
	private List<String> assets = new ArrayList<>();
	
	public StockMarket(){
		setAssets();
	}
	
	@Override
	public String getMarketName() {
		return STOCK_MARKET;
	}

	@Override
	public void setAssets() {
		List<String> listFromFile;
		try {
			listFromFile = FileParser.readETFList();
			for(int z = 0; z < listFromFile.size(); z++){
				this.assets.add(listFromFile.get(z));
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
	public String toString(){
		return this.marketName + ": " + this.assets;
	}

	@Override
	public void setMarketName(String marketName) {
		// TODO Auto-generated method stub
		
	}
	
	

}
