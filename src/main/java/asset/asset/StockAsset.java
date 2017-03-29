package asset;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import market.Market;
import asset.StockPriceList.StockChartData;;

public class StockAsset implements Asset {
	
	private String assetName;
	
	private List<StockChartData> allPriceList = new ArrayList<>();
	
	private List<BigDecimal> closeList = new ArrayList<>();

	public StockAsset(Market market){
		this.assetName = market.getAsset();
		setPriceList();
	}

	@Override
	public String getAsset() {
		return this.assetName;
	}

	@Override
	public void setCloseList() {
		for(int x = 0; x < allPriceList.size();x++){
			closeList.add(allPriceList.get(x).getClose());
		}
	}

	@Override
	public List<?> getCloseList() {
		return this.closeList;
	}

	@Override
	public void setAsset(String assetName) {
		this.assetName = assetName;		
	}

	@Override
	public void setPriceList() {
		try {
			StockPriceList priceList = new StockPriceList(this.assetName);
			this.allPriceList = priceList.stockPriceList;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<StockChartData> getPriceList() {
		return this.allPriceList;
	}

}
