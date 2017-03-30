package entry;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Collections;
import java.util.List;

import asset.StockChartData;

public class Entry {
	
	Boolean entry = false;
	String direction = null;
	int location;
	
	StockChartData stockChartData;
	
	public Entry(List<BigDecimal> priceList){
		entry = setEntry(priceList);
	}

	public Boolean setEntry(List<BigDecimal> priceList){
		BigDecimal currentDay = priceList.get(priceList.size() - 1);
		BigDecimal previousDay = Collections.max(priceList);
		if(priceList.get(priceList.size() - 1).compareTo(Collections.max(priceList)) == 0){
			setDirection("long");
			return true;
		}else if(priceList.get(priceList.size() - 1).equals(Collections.min(priceList))){
			setDirection("short");
			return true;
		}
		
		return false;
	}
	
	public void setDirection(String direction){
		this.direction = direction;
	}
	
	public String getDirection(){
		return this.direction;
	}
	
	public Boolean isEntry(){
		return entry;
	}
	
	public int getLocation(){
		return this.location;
	}
	
	public void setLocation(int location){
		this.location = location;
	}
	
	public void setStockChartData(StockChartData stockChartData){
		this.stockChartData = stockChartData;
	}
	
	@Override
	public String toString(){
		return "New Entry: " + this.direction + " " + this.stockChartData.getDate() + " " + this.stockChartData.getClose();
	}
	
	
}
