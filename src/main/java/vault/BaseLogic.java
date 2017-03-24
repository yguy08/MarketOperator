package vault;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;
import org.knowm.xchange.poloniex.service.PoloniexMarketDataServiceRaw;

import javafx.collections.ObservableList;
import operator.Asset;
import operator.Entry;
import operator.TradingSystem;

public class BaseLogic extends Main {
	
	static List<PoloniexChartData> priceList;
	
	public static void populateMarketList(ObservableList<String> stats){
		for(int x = 0;x < TradingSystem.marketList.size();x++){
			stats.add(TradingSystem.marketList.get(x).toString());
		}

	}
	
	public static void populateEntryList(ObservableList<String> stats) throws IOException{
		String assetName;
    	long dateFrom = new Date().getTime() / 1000 - (TradingSystem.HIGH_LOW * 24 * 60 * 60);
    	
    	List<CurrencyPair> marketList = TradingSystem.marketList;
		
		for(int x = 0; x < marketList.size();x++){
			
			assetName = TradingSystem.marketList.get(x).toString();
			
			priceList = TradingSystem.setCustomPriceList((PoloniexMarketDataServiceRaw) TradingSystem.dataService, assetName, dateFrom);
			
			Asset asset = new Asset(assetName, priceList);
			System.out.println(("Current Price: " + asset.getPrice()));
		
			//start with current price -> go backwards
			Entry entry = new Entry(asset.getName(), asset.getPriceList());
			
			if(entry.entryList.size() > 0){
				stats.add(" ********** MARKET TO WATCH ********** ");
				stats.add(asset.getName() + ": " + entry.entryList.get(0).getClose() + " @ " + TradingSystem.HIGH_LOW + " high "
						+ entry.entryList.get(0).getDate());
				//priceList = setCustomPriceList((PoloniexMarketDataServiceRaw) dataService, assetName, longerDate);
				//Position position = new Position(assetName, priceList);
				//System.out.println("ATR: " + position.trueRange);
				//System.out.println("Buy Amount: " + position.entrySize + " For Total: " + position.entrySize.multiply(entry.entryList.get(0).getClose()));
				//System.out.println("***** END ****");
			}else{
				stats.add("---------------------------------------");
				stats.add(asset.getName() + " Not at a high, skip...");
			}
			
		}
		
	}

}
