package operator;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;

import bitcoin.PoloEntry;
import truerange.Truerange;

public class Entry {
	
	String name;
	public int start = TradingSystem.HIGH_LOW;
	public int next;
	
	public BigDecimal longList;
	public BigDecimal shortList;
	
	public BigDecimal entry;
	
	public BigDecimal trueRange;

	PoloEntry poloEntry;
	
	public Entry(String name, List<PoloniexChartData> priceList){
		this.name 		= name;
	}
	
	public Entry(Market market, int nextEntry) {
    	if(market.getName().equals("digits")){
    		next = getNextHighLow(market.poloAsset.getCloseList(), nextEntry);
    		trueRange = Truerange.setAverageTrueRange(market.poloAsset.getCloseList(),next);
    	}
    }
	
	public int getNextHighLow(List<BigDecimal> closeList, int nextEntry){
		
		for(int x = nextEntry; x < closeList.size();x++){
			List<BigDecimal> subList = closeList.subList(x - TradingSystem.HIGH_LOW, x);
			if(subList.get(subList.size() - 1).equals(Collections.max(subList))){
				//longList.add(closeList.get(x));
				entry = closeList.get(x);
				return x;
			}else if(subList.get(subList.size() - 1).equals(Collections.min(subList))){
				//shortList.add(closeList.get(x));
				entry = closeList.get(x);
				return x;
			}
		}
		
		return closeList.size();
	}
}

