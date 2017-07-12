package price;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Collections;
import org.junit.Test;

import asset.Asset;
import util.DateUtils;
import vault.Config;

public class PriceJunit {
	
	@Test
	public void testDayHighLow(){
		Config.TestConfig();
		BigDecimal high;
		BigDecimal low;
		for(Asset asset : Config.getMarket().getAssetList()){
			for(int i = 0; i < asset.getPriceDataList().size();i++){
				//System.out.println(asset.getAssetName());
				if(asset.getPriceDataList().get(i).getHighLow() < 0){
					asset.setPriceSubList(i + (asset.getPriceDataList().get(i).getHighLow()), i);
					low = Collections.min(asset.getClosePriceListFromSubList());
					assertEquals(low,asset.getPriceDataList().get(i).getClose());
				}else if(asset.getPriceDataList().get(i).getHighLow() > 0){
					asset.setPriceSubList(i - (asset.getPriceDataList().get(i).getHighLow()), i);
					high = Collections.max(asset.getClosePriceListFromSubList());
					assertEquals(high,asset.getPriceDataList().get(i).getClose());
				}
			}
		}
	}
	
	@Test
	public void entries(){
		Config.TestConfig();
		for(Asset asset : Config.getMarket().getAssetList()){
			for(PriceData priceData : asset.getPriceDataList()){
				if(priceData.getHighLow() >= Config.getEntrySignalDays() && DateUtils.getNumDaysFromDateToToday(priceData.getDate()) < Config.getTimeFrameDays()){
					System.out.println(asset.getAssetName() + " " + priceData.toPlainString());
				}
			}
			
		}
	}

}
