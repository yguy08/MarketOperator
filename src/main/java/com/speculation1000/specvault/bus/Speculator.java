package com.speculation1000.specvault.bus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.speculation1000.specvault.market.Market;
import com.speculation1000.specvault.time.SpecVaultDate;

public class Speculator {

	public Speculator() {
		
	}
	
	public static List<Market> getLatestEntries(List<Market> marketList){
		Map<Market,List<BigDecimal>> closeMap = new HashMap<>();
		List<BigDecimal> closeList = new ArrayList<>();
		for(int i = 1; i < marketList.size();i++){
			Market m1 = marketList.get(i);
			Market m2 = marketList.get(i-1);
			String key1 = m1.getBase()+m1.getCounter()+m1.getExchange();
			String key2 = m2.getBase()+m2.getCounter()+m2.getExchange();
			if(key1.equalsIgnoreCase(key2)){
				closeList.add(m2.getClose());
			}else{
				List<BigDecimal> tmp = new ArrayList<>();
				for(BigDecimal bd : closeList){
					tmp.add(bd);
				}
				closeMap.put(m1,tmp);
				closeList.clear();
			}
		}
		
		List<Market> entryList = new ArrayList<>();
		for(Map.Entry<Market, List<BigDecimal>> e : closeMap.entrySet()){
			BigDecimal maxClose = Collections.max(e.getValue());
			BigDecimal minClose = Collections.min(e.getValue());
			BigDecimal current = e.getValue().get(e.getValue().size()-1);
			if(current.compareTo(maxClose) >= 0){
				Market market = e.getKey();
				entryList.add(market);
			}else if(current.compareTo(minClose) <= 0){
				Market market = e.getKey();
				entryList.add(market);
			}
		}
		return entryList;
		
	}

}
