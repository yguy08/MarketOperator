package market;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utils.FileParser;

public class PoloniexOffline implements Market {
	
	private String marketName = Market.STOCK_MARKET;
	private List<String> assets = new ArrayList<>();
	
	public PoloniexOffline(){
		setAssets();
	}

	@Override
	public String getMarketName() {
		return POLONIEX_OFFLINE;
	}

	@Override
	public void setAssets() {
		List<String> listFromFile;
		try {
			listFromFile = FileParser.readMarketList(this.getMarketName());
			for(int z = 0; z < listFromFile.size(); z++){
				this.assets.add(listFromFile.get(z));
			}
		} catch (IOException e) {
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

}
