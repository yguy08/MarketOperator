package market;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import vault.Config;

public class MarketFactoryJunit {
	
	static{		
		Config.ConfigSetUp();
	}
	
	@Test
	public void testCreateBitcoinMarket(){
		Config.setMarket(MarketsEnum.BITCOIN);
		if(Config.isConnected()){
			System.out.println("Creating Online Bitcoin Market");
			assertEquals(MarketsEnum.BITCOIN.getMarketName(), Config.getMarket().getMarketName());
		}else{
			System.out.println("Creating Offline Bitcoin Market");
			assertEquals(MarketsEnum.BITCOIN_OFFLINE.getMarketName(), Config.getMarket().getMarketName());
		}
	}
	
	@Test
	public void testCreateEthereumMarket(){
		Config.setMarket(MarketsEnum.ETHEREUM);		
		if(Config.isConnected()){
			System.out.println("Creating Online Ethereum Market");
			assertEquals(MarketsEnum.ETHEREUM.getMarketName(), Config.getMarket().getMarketName());
		}else{
			System.out.println("Creating Offline Ethereum Market");
			assertEquals(MarketsEnum.ETHEREUM_OFFLINE.getMarketName(), Config.getMarket().getMarketName());
		}
	}
	
	@Test
	public void testCreateDollarMarket(){
		Config.setMarket(MarketsEnum.DOLLAR);
		if(Config.isConnected()){
			System.out.println("Creating Online Dollar Market");
			assertEquals(MarketsEnum.DOLLAR.getMarketName(), Config.getMarket().getMarketName());
		}else{
			System.out.println("Creating Offline Dollar Market");
			assertEquals(MarketsEnum.DOLLAR_OFFLINE.getMarketName(), Config.getMarket().getMarketName());
		}
	}

}
