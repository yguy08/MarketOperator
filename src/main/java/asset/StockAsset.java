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
	List<BigDecimal> closeList	= new ArrayList<>();
	
	public StockAsset(Market market, String assetName){
		this.marketName = market.getMarketName();
		this.assetName	= assetName;
		setPriceList(this.assetName);
		setCloseList();
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
			for(int z = myString.size() - 1; z >= 0; z--){
				String[] split = myString.get(z).split(",");
				StockChartData chartData = new StockChartData((String) split[0], new BigDecimal(split[4]), 
						new BigDecimal(split[2]), new BigDecimal(split[3]));
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

	@Override
	public void setCloseList() {
		//reverse and add close
		for(int x = 0; x < priceList.size();x++ ){
			this.closeList.add(this.priceList.get(x).getClose());
		}
	}

	@Override
	public List<BigDecimal> getCloseList() {
		// TODO Auto-generated method stub
		return this.closeList;
	}
	

}
