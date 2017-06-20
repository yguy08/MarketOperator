package market;

import vault.VaultStart;

public class MarketFactory {
	
	private static Market market;
	
	//called when app first loads
	public static Market createMarket(MarketsEnum marketEnum){
		if(VaultStart.isConnected()){			
			switch(marketEnum){
			case BITCOIN:
				market = BitcoinMarket.createOnlineBitcoinMarket();
				return BitcoinMarket.createOnlineBitcoinMarket();
			case ETHEREUM:
				return EthereumMarket.createOnlineEthereumMarket();
			case DOLLAR:
				return DollarMarket.createOnlineDollarMarket();
			default:
				return BitcoinMarket.createOnlineBitcoinMarket();
			}			
		}else{
			switch(marketEnum){
			case BITCOIN:
				market = BitcoinMarket.createOfflineBitcoinMarket();
				return BitcoinMarket.createOfflineBitcoinMarket();
			case ETHEREUM:
				return EthereumMarket.createOfflineEthereumMarket();
			case DOLLAR:
				return DollarMarket.createOfflineDollarMarket();
			default:
				return BitcoinMarket.createOfflineBitcoinMarket();
			}
		}
	}
	
	public static Market getMarket(){
		if(market!=null){
			return market;
		}else{
			return null;
		}
	}	
}
