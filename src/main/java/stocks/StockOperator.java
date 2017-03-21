package stocks;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import operator.TradingSystem;

public class StockOperator {
	
	public static void stockWatchList() throws IOException{
		StockPriceList stockPriceList = new StockPriceList("Fb-0512-0317.csv");
		List<StockChartData> stockChartData = new ArrayList<>();
		for(int x = 0; x < StockPriceList.getCloseList().size();x++){
			stockChartData.add(new StockChartData(StockPriceList.getDateList().get(x), StockPriceList.getCloseList().get(x), 
					StockPriceList.getHighList().get(x), StockPriceList.getLowList().get(x)));
		}

		StockAsset stockAsset = new StockAsset("FB", stockChartData);
		System.out.println("Current Price: " + stockAsset.getPrice());

		StockEntry stockEntry = new StockEntry(stockAsset.getName(), stockAsset.getPriceList());

		if(stockEntry.entryList.get(0).equals(stockAsset.getPriceList().get(0))){
			System.out.println("***** MARKET TO WATCH *****");
			System.out.println("***** " + stockAsset.getName());
			System.out.println("***** " + TradingSystem.HIGH_LOW + " day high");
			System.out.println("***** " + "Date: " + stockEntry.entryList.get(0).getDate());
			System.out.println("***** ENTRY STATS *****");
		}else{
			System.out.println("Not currently at a high...");
			System.out.println("Last high was: " + stockEntry.entryList.get(0).getDate() + " "
					+ stockEntry.entryList.get(0).getClose());
		}
	}

}
