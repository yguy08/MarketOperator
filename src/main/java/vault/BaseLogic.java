package vault;

import java.io.IOException;
import java.math.MathContext;
import java.util.List;
import java.math.BigDecimal;

import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;
import bitcoin.PoloAsset;
import bitcoin.PoloEntry;
import bitcoin.PoloExit;
import bitcoin.PoloMarket;
import bitcoin.PoloPosition;
import javafx.collections.ObservableList;
import operator.TradingSystem;

public class BaseLogic extends Main {
	
	static List<PoloniexChartData> priceList;
	
	public static void populateMarketList(ObservableList<String> stats){
		PoloMarket polo = new PoloMarket();
		for(int x = 0;x < polo.assetList.size();x++){
			stats.add(polo.assetList.get(x).toString());
		}
	}
	
	public static void populateEntryList(ObservableList<String> stats) throws IOException{
    	PoloMarket polo = new PoloMarket();
    	
    	for(int x = 0; x < polo.assetList.size();x++){
			
    		PoloAsset asset = new PoloAsset(polo.assetList.get(x).toString(), polo);
			
    		//start with current price -> go backwards
			PoloEntry entry = new PoloEntry(asset);
			
			if(entry.entryList.size() > 0 && entry.isHigh(asset.priceList)){
				PoloPosition position = new PoloPosition(asset);
				StringBuilder sb = new StringBuilder();
				sb.append("***** MARKET TO WATCH: " + asset.name + ": ");
				sb.append("Price: " + entry.entryList.get(0).getClose() + " ");
				sb.append(" @ " + TradingSystem.HIGH_LOW + " high ");
				sb.append("Date: " + entry.entryList.get(0).getDate() + " ");
				sb.append("ATR: " + position.trueRange + " ");
				sb.append("Size: " + position.entrySize + " ");
				sb.append("Total: " + position.entrySize.multiply(entry.entryList.get(0).getClose(), MathContext.DECIMAL32));
				String t = sb.toString();
				stats.add(t);
			}
			if(entry.entryList.size() > 0 && entry.isHigh(asset.priceList) == false){
				stats.add(" ********** MARKET TO WATCH ********** ");
				stats.add(asset.name + ": " + entry.entryList.get(0).getClose() + " @ " + TradingSystem.HIGH_LOW + " low "
						+ entry.entryList.get(0).getDate());
			}
			if(entry.entryList.size() <= 0){
				stats.add("---------------------------------------");
				stats.add(asset.name + " Not at a high or low, skip...");
			}
		}
	}
	
	public static void populateExitList(ObservableList<String> stats) throws IOException{
		PoloMarket polo = new PoloMarket();
		for(int x = 0; x < polo.assetList.size();x++){
			PoloAsset asset = new PoloAsset(polo.assetList.get(x).toString(), polo);
			
			PoloExit exit = new PoloExit(asset);
			
			if(exit.exitList.size() > 0){
				stats.add("********** MARKET TO CLOSE **********");
				stats.add(asset.name + ": " + exit.exitList.get(0).getClose() + " @ " + TradingSystem.CLOSE + " low "
						+ exit.exitList.get(0).getDate());
			}else{
				stats.add("---------------------------------------");
				stats.add(asset.name + " Not at a low, skip...");
			}
		}
	}
}
