package asset;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import fileparser.FileParser;
import market.Market;

public class StockAsset implements Asset {
	
	String marketName;
	String assetName;
	List<StockChartData> priceList = new ArrayList<>();
	
	public StockAsset(Market market, String assetName){
		this.marketName = market.getMarketName();
		this.assetName	= assetName;
		setPriceList(this.assetName);
	}

	@Override
	public void setAsset(String assetName) {
		this.assetName = assetName;
	}

	@Override
	public String getAsset() {
		return this.assetName;
	}

	@Override
	public void setPriceList(String assetName) {
		List<String> myString;
		try {
			myString = FileParser.readYahooStockFileByLines(assetName);
			for(int z = 0; z < myString.size(); z++){
				String[] split = myString.get(z).split(",");
				StockChartData chartData = new StockChartData((String) split[0], new BigDecimal(split[2]), 
						new BigDecimal(split[3]), new BigDecimal(split[4]));
				priceList.add(chartData);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<StockChartData> getPriceList() {
		return this.priceList;
	}
	
	@Override
	public String toString(){
		return this.marketName + ": [ $" + this.assetName + " ] " + " " + this.priceList;
	}
	

}
