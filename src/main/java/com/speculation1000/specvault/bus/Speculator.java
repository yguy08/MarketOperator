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
		Map<String,List<BigDecimal>> closeMap = new HashMap<>();
		List<BigDecimal> closeList = new ArrayList<>();
		for(int i = 0; i < marketList.size();i++){
			//RETHINK
		}
		
		List<Market> entryList = new ArrayList<>();
		for(Map.Entry<String, List<BigDecimal>> e : closeMap.entrySet()){
			
		}
		return marketList;
		
	}

}
