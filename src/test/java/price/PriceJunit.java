package price;

import org.junit.Test;

import asset.Asset;
import asset.DigitalAsset;
import speculator.DigitalSpeculator;
import trade.Entry;
import vault.Config;

import static org.junit.Assert.assertEquals;

import java.util.List;

public class PriceJunit {	
	
	@Test
	public void testSetPriceDataList(){
		Config.TestConfig();
		Asset asset = DigitalAsset.createOfflineDigitalAsset("LTC/BTC");
		asset.setPriceDataList();
		assertEquals(asset.getPriceList().size(), asset.getPriceDataList().size());
	}

}
