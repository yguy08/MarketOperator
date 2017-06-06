package asset;

import org.junit.Test;
import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;

import market.BitcoinMarket;
import market.Market;
import speculator.DigitalSpeculator;
import speculator.Speculator;

import static org.junit.Assert.assertEquals;

import java.util.Random;

public class AssetJunit {
	
	private static final Random RANDOM = new Random();
	
	Market market = BitcoinMarket.createOfflineBitcoinMarket();
	
	int size = market.getAssetList().size()-1;
	
	Asset asset = market.getAssetList().get(RANDOM.nextInt(size));
	
	@Test
	public void testSubListSize(){
		Speculator speculator = new DigitalSpeculator();
		int startIndex;
		if(asset.getIndexOfLastRecordInPriceList() < speculator.getEntrySignalDays()){
			startIndex = 0;
		}else{
			startIndex = asset.getIndexOfLastRecordInPriceList() - speculator.getEntrySignalDays();
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

}
