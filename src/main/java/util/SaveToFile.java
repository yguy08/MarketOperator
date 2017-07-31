package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import asset.Asset;
import market.MarketInterface;

public class SaveToFile {
	
	public static void writeAssetPriceListToFile(Asset asset, List<?> priceList){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < priceList.size();i++){
			sb.append(priceList.get(i) + "\n");
		}
		String content = sb.toString();
		try {
			Files.write(Paths.get("src/main/resources/asset/" + asset.getAssetName().replace("/", "") + ".txt"), 
					content.getBytes());		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeMarketListToFile(MarketInterface market, List<Asset> assetList){
		StringBuilder sb = new StringBuilder();
		for(Asset a : assetList){
			sb.append(a.getAssetName() + "\n");
		}
		String content = sb.toString();
		try {
			Files.write(Paths.get("src/main/resources/market/" + market.getMarketName() + ".csv"), 
					content.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
