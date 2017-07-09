package asset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Random;

import org.junit.Test;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;

import market.BitcoinMarket;
import market.Market;
import price.PriceData;
import vault.Config;

public class AssetJunit {
	
	private static final Random RANDOM = new Random();
	
	Market market = BitcoinMarket.createOfflineBitcoinMarket();
	
	int size = market.getAssetList().size()-1;
	
	Asset asset = market.getAssetList().get(RANDOM.nextInt(size));
	
	@Test
	public void testSubListSize(){
		int startIndex;
		if(asset.getIndexOfLastRecordInPriceList() < Config.getEntrySignalDays()){
			startIndex = 0;
		}else{
			startIndex = asset.getIndexOfLastRecordInPriceList() - Config.getEntrySignalDays();
		}
		
		asset.setPriceSubList(startIndex, asset.getIndexOfLastRecordInPriceList());
		int fromSubList = asset.getIndexOfLastRecordInSubList();
		int fromPriceList = asset.getIndexOfLastRecordInPriceList();
		assertEquals(fromPriceList, fromSubList);
	}
	
	@Test
	public void testgetIndexOfCurrentRecord(){
		System.out.println("testGetIndexOfCurrentRecord(): Asset: " + asset.toString());
		
		int listSize = asset.getPriceList().size() - 1;
		int r = RANDOM.nextInt(listSize);
		asset.setPriceSubList(0, r);
		System.out.println("SubList: 0," + r);
		
		int priceListindexOfLastSubListRecord = asset.getIndexOfLastRecordInSubList();
		
		
		PoloniexChartData fromSubList = asset.getPriceList().get(r);
		PoloniexChartData fromPriceList = asset.getPriceList().get(priceListindexOfLastSubListRecord);
		
		System.out.println("Comparing: " + fromPriceList + " to " + fromSubList);
		
		assertEquals(fromPriceList, fromSubList);
	}
	
	@Test
	public void getIndexOfLastRecordInPriceList(){
		int expected = asset.getPriceList().size() - 1;
		int actual = asset.getIndexOfLastRecordInPriceList();
		
		PoloniexChartData fromExpected = asset.getPriceList().get(expected);
		PoloniexChartData fromActual = asset.getPriceList().get(actual);
		
		System.out.println("getIndexOfLastRecordInPriceList(): Asset: " + asset.toString());
		System.out.println("Comparing: " + fromExpected + " to " + fromActual);
		
		assertEquals(fromExpected, fromActual);
	}
	
	@Test
	public void testSetPriceDataList(){
		asset.setPriceDataList();
		for(PriceData priceData : asset.getPriceDataList()){
			assertNotNull(priceData.getTrueRange());
		}
	}

}
