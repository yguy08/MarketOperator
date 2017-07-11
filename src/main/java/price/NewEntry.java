package price;

import java.util.List;

import asset.Asset;
import speculator.DigitalSpeculator;
import speculator.Speculator;
import trade.Entry;
import vault.Config;

public class NewEntry {
	
	public static void setEntries(Asset asset, List<PriceData> priceDataList){
		Entry entry;
		int i = asset.getStartIndex(Config.getEntrySignalDays(), Config.getTimeFrameDays());
		Speculator speculator = new DigitalSpeculator();
		for(int x = i;x < asset.getPriceDataList().size();x++){
			asset.setPriceSubList(x - Config.getEntrySignalDays(), x);
			entry = new Entry(asset, speculator);
			if(entry.isEntry()){
				priceDataList.get(x).setEntry(true);
			}				
		}
	}
	
	public static void main(String[]args){
		Config.TestConfig();
		for(Asset asset : Config.getMarket().getAssetList()){
			for(PriceData priceData : asset.getPriceDataList()){
				if(priceData.isEntry()){
					System.out.println(asset.getAssetName() + " " + priceData.toString());
				}
				
			}
		}
	}
	
	

}
