package market;

import vault.Config;

public class MarketFactory {
	
	//called when app first loads
	public static Market createMarket(MarketsEnum marketEnum){
		if(Config.isConnected()){			
			switch(marketEnum){
			case BITCOIN:
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
		return Config.getMarket();
	}	
}
