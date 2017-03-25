package vault;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;
import org.knowm.xchange.poloniex.service.PoloniexMarketDataServiceRaw;

import bitcoin.PoloAsset;
import bitcoin.PoloEntry;
import bitcoin.PoloMarket;
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
			
			if(entry.entryList.size() > 0){
				stats.add(" ********** MARKET TO WATCH ********** ");
				stats.add(asset.name + ": " + entry.entryList.get(0).getClose() + " @ " + TradingSystem.HIGH_LOW + " high "
						+ entry.entryList.get(0).getDate());
				//priceList = setCustomPriceList((PoloniexMarketDataServiceRaw) dataService, assetName, longerDate);
				//Position position = new Position(assetName, priceList);
				//System.out.println("ATR: " + position.trueRange);
				//System.out.println("Buy Amount: " + position.entrySize + " For Total: " + position.entrySize.multiply(entry.entryList.get(0).getClose()));
				//System.out.println("***** END ****");
			}else{
				stats.add("---------------------------------------");
				stats.add(asset.name + " Not at a high, skip...");
			}
		}
	}
}
