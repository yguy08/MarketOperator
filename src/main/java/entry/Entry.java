package entry;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class Entry {
	
	Boolean entry = false;
	String direction = null;
	int location;
	
	public Entry(List<BigDecimal> priceList){
		entry = setEntry(priceList);
	}

	public Boolean setEntry(List<BigDecimal> priceList){
		if(priceList.get(priceList.size() - 1).equals(Collections.max(priceList))){
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
	
	
}
