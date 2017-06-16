package market;

import vault.VaultStart;

public class MarketFactory {
	
	//called when app first loads
	public static Market createMarket(MarketsEnum marketEnum){
		
		if(VaultStart.isConnected()){			
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
	
	//create market (String marketName)
	
	//create online dollar market
	
	//create offline dollar market
	
	//create online ethereum market
	
	//create offline ethereum market	
	
	
}
