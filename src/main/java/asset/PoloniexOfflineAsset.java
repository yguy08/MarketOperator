package asset;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import market.Market;
import utils.PoloniexOfflineAdapter;

public class PoloniexOfflineAsset implements Asset {
	
	String marketName;
	String assetName;
	List<PoloniexOfflineChartData> priceList = new ArrayList<>();
	List<BigDecimal> closeList	= new ArrayList<>();
	List<BigDecimal> lowList = new ArrayList<>();
	List<BigDecimal> highList = new ArrayList<>();
	
	
	public List<PoloniexOfflineChartData> priceSubList;
	
	public PoloniexOfflineAsset(Market market, String assetName){
		this.marketName = market.getMarketName();
		this.assetName	= assetName;
		setPriceList(this.assetName);
		setCloseList();
		setLowList();
		setHighList();
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
		try {
			this.priceList = PoloniexOfflineAdapter.getPoloOfflineChartData(assetName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<PoloniexOfflineChartData> getPriceList() {
		return this.priceList;
	}

	@Override
	public void setCloseList() {
		for(int x = 0; x < priceList.size();x++ ){
			this.closeList.add(this.priceList.get(x).getClose());
		}
	}

	@Override
	public List<BigDecimal> getCloseList() {
		return this.closeList;
	}

	@Override
	public void setPriceSubList(int start, int end) {
		this.priceSubList = (List<PoloniexOfflineChartData>) this.priceList.subList(start, end);
	}

	@Override
	public List<PoloniexOfflineChartData> getPriceSubList() {
		return this.priceSubList;
	}

	@Override
	public void setLowList() {
		for(int x = 0; x < priceList.size();x++ ){
			this.lowList.add(this.priceList.get(x).getLow());
		}
	}

	@Override
	public List<BigDecimal> getLowList() {
		return this.lowList;
	}

	@Override
	public void setHighList() {
		for(int x = 0; x < priceList.size();x++ ){
			this.highList.add(this.priceList.get(x).getHigh());
		}
	}

	@Override
	public List<BigDecimal> getHighList() {
		return this.highList;
	}
	
	@Override
	public String toString(){
		return this.marketName + ": [ $" + this.assetName + " ] " + " " + this.priceList;
	}

}
