package price;

import org.junit.Test;

import asset.Asset;
import asset.DigitalAsset;
import vault.Config;

import static org.junit.Assert.assertEquals;

public class PriceJunit {	
	
	@Test
	public void testSetPriceDataList(){
		Config.TestConfig();
		Asset asset = DigitalAsset.createOfflineDigitalAsset("LTC/BTC");
		asset.setPriceDataList();
		assertEquals(asset.getPriceList().size(), asset.getPriceDataList().size());
	}

}
