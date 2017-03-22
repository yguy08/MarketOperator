package vault;

import java.io.IOException;
import java.util.List;

import MarketChartData.StockChartData;
import javafx.collections.ObservableList;
import stocks.Speculation;
import stocks.StockAsset;
import stocks.StockEntry;

public class BaseLogic extends Main {
	
	public static void populateEntryList(ObservableList<String> stats) throws IOException{
		
		//NOTE FOR HOME: 
		List<StockChartData> stockChartData = Speculation.setStockChartData("Fb-0512-0317.csv");
		
		StockAsset stockAsset = new StockAsset("FB", stockChartData);
		
		StockEntry stockEntry = new StockEntry(stockAsset.getName(), stockAsset.getPriceList());
		
		if(stockEntry.entryList.get(0).equals(stockAsset.getPriceList().get(0))){
			stats.add(stockAsset.getName() + " @ " + Speculation.HIGH_LOW + " day high!" + " Price: " 
		+ stockEntry.entryList.get(0).getClose() + " Date: " + stockEntry.entryList.get(0).getDate());
		}else{
			stats.add("Not currently at a high...");
			stats.add("Last high was: " + stockEntry.entryList.get(0).getDate() + " "
					+ stockEntry.entryList.get(0).getClose());
		}
		
		
	}

}
