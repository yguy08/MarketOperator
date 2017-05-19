package entry;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import asset.Asset;
import speculator.Speculator;

public class BitcoinEntry implements Entry {
	
	List<Integer> entryIndexList = new ArrayList<>();
	
	public static BitcoinEntry createBitcoinEntry(Asset asset, Speculator speculator) {
		BitcoinEntry bEntry = new BitcoinEntry();
		bEntry.findAllEntries(asset, speculator);
		return bEntry;
	}

	@Override
	public void findAllEntries(Asset asset, Speculator speculator) {
		List<BigDecimal> priceList = new ArrayList<>();
		int latestPriceIndex = (asset.getAssetPriceList().size() - 1);
		
		for(int i = latestPriceIndex; i > 0 && i > latestPriceIndex - speculator.getTimeFrameDays();i--){
			asset.setAssetPriceSubList(i - speculator.getEntrySignalDays(), i + 1);
			
			for(int x = 0; x < asset.getAssetPriceSubList().size(); x++){
				priceList.add(asset.getAssetPriceSubList().get(x).getClose());
			}
			
			if(asset.getAssetPriceSubList().get(speculator.getEntrySignalDays()).getClose().compareTo(Collections.max(priceList)) == 0){
				entryIndexList.add(i);
			}
		}
	}

	@Override
	public List<Integer> getEntryIndex() {
		return entryIndexList;
	}
	
	

}
