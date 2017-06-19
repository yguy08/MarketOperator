package market;

import org.junit.Test;

import vault.VaultStart;

import static org.junit.Assert.assertEquals;

public class MarketFactoryJunit {
	
	static{		
		VaultStart.testConnection();
	}
	
	@Test
	public void testCreateBitcoinMarket(){
		Market bitcoinMarket = MarketFactory.createMarket(MarketsEnum.BITCOIN);
		if(VaultStart.isConnected()){
			System.out.println("Creating Online Bitcoin Market");
			assertEquals(MarketsEnum.BITCOIN.getMarketName(), bitcoinMarket.getMarketName());
		}else{
			System.out.println("Creating Offline Bitcoin Market");
			assertEquals(MarketsEnum.BITCOIN_OFFLINE.getMarketName(), bitcoinMarket.getMarketName());
		}
	}
	
	@Test
	public void testCreateEthereumMarket(){
		Market ethereumMarket = MarketFactory.createMarket(MarketsEnum.ETHEREUM);		
		if(VaultStart.isConnected()){
			System.out.println("Creating Online Ethereum Market");
			assertEquals(MarketsEnum.ETHEREUM.getMarketName(), ethereumMarket.getMarketName());
		}else{
			System.out.println("Creating Offline Ethereum Market");
			assertEquals(MarketsEnum.ETHEREUM_OFFLINE.getMarketName(), ethereumMarket.getMarketName());
		}
	}
	
	@Test
	public void testCreateDollarMarket(){
		Market dollarMarket = MarketFactory.createMarket(MarketsEnum.DOLLAR);
		if(VaultStart.isConnected()){
			System.out.println("Creating Online Dollar Market");
			assertEquals(MarketsEnum.DOLLAR.getMarketName(), dollarMarket.getMarketName());
		}else{
			System.out.println("Creating Offline Dollar Market");
			assertEquals(MarketsEnum.DOLLAR_OFFLINE.getMarketName(), dollarMarket.getMarketName());
		}
	}

}
