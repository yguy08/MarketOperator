package market;

import org.junit.Test;

import vault.Config;
import vault.VaultStart;

import static org.junit.Assert.assertEquals;

public class MarketFactoryJunit {
	
	static{		
		Config.setConnected();
	}
	
	@Test
	public void testCreateBitcoinMarket(){
		MarketFactory.createMarket(MarketsEnum.BITCOIN);
		if(Config.isConnected()){
			System.out.println("Creating Online Bitcoin Market");
			assertEquals(MarketsEnum.BITCOIN.getMarketName(), MarketFactory.getMarket().getMarketName());
		}else{
			System.out.println("Creating Offline Bitcoin Market");
			assertEquals(MarketsEnum.BITCOIN_OFFLINE.getMarketName(), MarketFactory.getMarket().getMarketName());
		}
	}
	
	@Test
	public void testCreateEthereumMarket(){
		MarketFactory.createMarket(MarketsEnum.ETHEREUM);		
		if(Config.isConnected()){
			System.out.println("Creating Online Ethereum Market");
			assertEquals(MarketsEnum.ETHEREUM.getMarketName(), MarketFactory.getMarket().getMarketName());
		}else{
			System.out.println("Creating Offline Ethereum Market");
			assertEquals(MarketsEnum.ETHEREUM_OFFLINE.getMarketName(), MarketFactory.getMarket().getMarketName());
		}
	}
	
	@Test
	public void testCreateDollarMarket(){
		MarketFactory.createMarket(MarketsEnum.DOLLAR);
		if(Config.isConnected()){
			System.out.println("Creating Online Dollar Market");
			assertEquals(MarketsEnum.DOLLAR.getMarketName(), MarketFactory.getMarket().getMarketName());
		}else{
			System.out.println("Creating Offline Dollar Market");
			assertEquals(MarketsEnum.DOLLAR_OFFLINE.getMarketName(), MarketFactory.getMarket().getMarketName());
		}
	}

}
