package backtest;

import java.math.BigDecimal;

import operator.Entry;

public class Result {
	
	Boolean isEnoughHistory = true;
	Boolean haveData;
	
	BigDecimal trueRange;
	BigDecimal entry;
	
	Result(Entry entry){

	}
	
	public void haveData(Boolean b){
		haveData = false;
	}
	
	public void setEntry(Entry entry){
	}
	
	public void setEnoughHistory(Boolean b){
		isEnoughHistory = b;
	}
	
	public void setTrueRange(Entry entry){
		trueRange = entry.trueRange;
	}
	
	

}
