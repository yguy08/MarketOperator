package util;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import asset.PoloniexOfflineChartData;
import market.Market;

public class FileParser {
	
	public static List<String> readTextFileByLines(String fileName) throws IOException{
		List<String> lines = Files.readAllLines(Paths.get(fileName));
		return lines;
	}
	
	public static List<String> readYahooStockFileByLines(String fileName) throws IOException{
		String csv = "StockFiles/" + fileName + ".csv";
		List<String> lines = Files.readAllLines(Paths.get(csv));
		lines.remove(0);
		return lines;
	}
	
	public static List<String> readStockTickerList() throws IOException{
		List<String> lines = Files.readAllLines(Paths.get("StockFiles/SP500.csv"));
		return lines;
	}
	
	public static List<String> readETFList() throws IOException{
		List<String> lines = Files.readAllLines(Paths.get("StockFiles/TopEtfs.csv"));
		return lines;
	}
	
	public static List<String> readMarketList(String marketName) throws IOException{
		List<String> lines = Files.readAllLines(Paths.get(marketName + "/MarketList.csv"));
		return lines;
	}
	
	public static List<String> generateETFYahooURL() {
		try {
			List<String> etfs = readETFList();
			List<String> urls = new ArrayList<>();
			for(int i = 0; i < etfs.size();i++){
				String url = "http://chart.finance.yahoo.com/table.csv?s=" +etfs.get(i)+"&a=3&b=5&c=2016&d=3&e=5&f=2017&g=d&ignore=.csv";
				urls.add(url);
			}
			return urls;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static List<String> readPoloniexFile(String assetName){
		try{
			List<String> poloJSON = Files.readAllLines(Paths.get(assetName));
			return poloJSON;
		}catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<String> generatePoloURL() {
		try {
			List<String> poloList = readMarketList(Market.POLONIEX_OFFLINE);
			List<String> urls = new ArrayList<>();
			for(int i = 0; i < poloList.size();i++){
				String url = "https://poloniex.com/public?command=returnChartData&currencyPair=BTC_"+poloList.get(i)+"&start=1389716471&end=9999999999&period=86400";
				urls.add("start " + "\"polo\" " + "\"" + url + "\"");
			}
			return urls;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public static List<PoloniexOfflineChartData> parsePoloFile(String assetName) throws ParseException{
		List<String> chartList = FileParser.readPoloniexFile(assetName);
		List<PoloniexOfflineChartData> pChartData = new ArrayList<>();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date;
		BigDecimal high = new BigDecimal(0.00);
		BigDecimal low = new BigDecimal(0.00);
		BigDecimal open = new BigDecimal(0.00);
		BigDecimal close = new BigDecimal(0.00);
		BigDecimal volume = new BigDecimal(0.00);
		BigDecimal quoteVolume = new BigDecimal(0.00);
		BigDecimal weightedAverage = new BigDecimal(0.00);
	    for(int i = 0; i < chartList.size();i++){
			String replace = chartList.get(i).toString(); 
			System.out.println(replace);
			String removePoloChartData = replace.replaceAll("[(PoloniexChartData)(date)(high)(low)(open)(close)(volume)(quoteVolume)(weightedAverage)(=)(\\[\\])]", "");
			System.out.println(removePoloChartData);
			String myArr[] = removePoloChartData.split(",");
			date = format.parse(myArr[0].trim());
			high = new BigDecimal(myArr[1].trim());
			high.setScale(8, RoundingMode.HALF_DOWN);
			low = new BigDecimal(myArr[2].trim());
			low.setScale(8, RoundingMode.HALF_DOWN);
			open = new BigDecimal(myArr[3].trim());
			open.setScale(8, RoundingMode.HALF_DOWN);
			close = new BigDecimal(myArr[4].trim());
			close.setScale(8, RoundingMode.HALF_DOWN);
			volume = new BigDecimal(myArr[5].trim());
			volume.setScale(8, RoundingMode.HALF_DOWN);
			quoteVolume = new BigDecimal(myArr[6].trim());
			quoteVolume.setScale(8, RoundingMode.HALF_DOWN);
			weightedAverage = new BigDecimal(myArr[7].trim());
			weightedAverage.setScale(8, RoundingMode.HALF_DOWN);
			PoloniexOfflineChartData poloOffline = new PoloniexOfflineChartData(date, high,
					low, open, close, volume, quoteVolume, weightedAverage);
			pChartData.add(poloOffline);
	    }
	    
	    return pChartData;
		
	}
	
	
	
	
}
