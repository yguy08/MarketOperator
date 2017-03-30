package entry;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import asset.Asset;
import asset.StockChartData;
import market.Market;

public class StockEntry implements Entry {
	
	Market market;
	Asset asset;
	
	List<StockChartData> priceSubList = new ArrayList<>();
	List<BigDecimal> subListClose = new ArrayList<>();
	
	String Date;
	BigDecimal currentPrice;
	BigDecimal maxPrice;
	BigDecimal minPrice;
	int locationIndex;
	String direction = null;
	Boolean isEntry = false;
	
	
	public StockEntry(Market market, Asset asset){
		this.market = market;
		this.asset	= asset;
		setPriceSubList(this.asset);
		setDate();
		setCurrentPrice();
		setMaxPrice();
		setMinPrice();
		setLocationAsIndex();
		setEntry();
		setDirection();
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(this.market.getMarketName() + ":");
		sb.append(" [$" + this.asset.getAsset() + "]");
		sb.append(" Date:" + this.Date);
		sb.append(" Current price:" + this.currentPrice);
		sb.append(" Max price:" + this.maxPrice);
		sb.append(" Min price:" + this.minPrice);
		sb.append(" Entry:" + this.isEntry);
		sb.append(" Direction:" + this.direction);
		sb.append(" Index:" + this.locationIndex);
		return sb.toString();
	}
	
	@Override
	public void setEntry() {
		if(this.currentPrice.compareTo(this.maxPrice) == 0){
			this.isEntry = true;
		}else if(this.currentPrice.compareTo(this.minPrice) == 0){
			this.isEntry = true;
		}else{
			this.isEntry = false;
		}
	}

	@Override
	public Entry getEntry() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDate() {
		this.Date = this.priceSubList.get(this.priceSubList.size() - 1).getDate();
	}

	@Override
	public String getDate() {
		return this.Date;
	}

	@Override
	public void setCurrentPrice() {
		this.currentPrice = this.priceSubList.get(this.priceSubList.size() - 1).getClose();		
	}

	@Override
	public BigDecimal getCurrentPrice() {
		return this.currentPrice;
	}

	@Override
	public void setMaxPrice() {
		List<BigDecimal> maxList = new ArrayList<>();
		for(int x = 0; x < this.priceSubList.size(); x++){
			maxList.add(this.priceSubList.get(x).getClose());
		}
		
		this.maxPrice = Collections.max(maxList);
	}

	@Override
	public BigDecimal getMaxPrice() {
		return this.maxPrice;
	}

	@Override
	public void setMinPrice() {
		List<BigDecimal> minList = new ArrayList<>();
		for(int x = 0; x < this.priceSubList.size(); x++){
			minList.add(this.priceSubList.get(x).getClose());
		}
		
		this.minPrice = Collections.min(minList);		
	}

	@Override
	public BigDecimal getMinPrice() {
		return this.minPrice;
	}
	
	@Override
	public void setDirection() {
		if(isEntry() && this.currentPrice.compareTo(this.maxPrice) == 0){
			this.direction = "long";
		}else if(isEntry() && this.currentPrice.compareTo(this.minPrice) == 0){
			this.direction = "short";
		}else{
			this.direction = "Not an Entry";
		}
	}

	@Override
	public String getDirection() {
		return this.direction;
	}

	@Override
	public void setLocationAsIndex() {
		this.locationIndex = this.asset.getPriceList().indexOf(this.priceSubList.get(this.priceSubList.size() - 1));
	}

	@Override
	public int getLocationIndex() {
		return this.locationIndex;
	}

	@Override
	public Boolean isEntry() {
		return this.isEntry;
	}

	@Override
	public void setPriceSubList(Asset asset) {
		this.priceSubList = (List<StockChartData>) asset.getPriceSubList();
	}
	
	

}
