package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import asset.Asset;
import market.Market;

public class SaveToFile {
	
	public static void writeToTextFile(String fileName, String content) throws IOException {
        Files.write(Paths.get("Results/" + fileName + ".txt"), content.getBytes(), StandardOpenOption.CREATE);
	}
	
	public static void writeAssetPriceListToFile(Asset asset, List<?> priceList){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < priceList.size();i++){
			sb.append(priceList.get(i) + "\n");
		}
		String content = sb.toString();
		try {
			Files.write(Paths.get(asset.getMarketName() + "/" + asset.getAssetName().replace("/", "") + ".txt"), 
					content.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeAssetPriceListToFile(Asset asset, String priceList){
		
		String content = priceList;
		try {
			Files.write(Paths.get(asset.getMarketName() + "/" + asset.getAssetName().replace("/", "") + ".txt"), 
					content.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeMarketListToFile(Market market, List<String> assetList){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < assetList.size();i++){
			sb.append(assetList.get(i) + "\n");
		}
		String content = sb.toString();
		try {
			Files.write(Paths.get(market.getMarketName() + "/" + "MarketList" + ".csv"), 
					content.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeToMarketLog(String content){
		try {
			Files.write(Paths.get(Market.DIGITAL_MARKET + "/" + "LOG" + ".txt"), 
					content.getBytes(), StandardOpenOption.APPEND);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
