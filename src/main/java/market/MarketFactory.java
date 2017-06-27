package market;

import vault.VaultStart;

public class MarketFactory {
	
	private static Market market;
	
	//called when app first loads
	public static void createMarket(MarketsEnum marketEnum){
		if(VaultStart.isConnected()){			
			switch(marketEnum){
			case BITCOIN:
				market = BitcoinMarket.createOnlineBitcoinMarket();
				break;
			case ETHEREUM:
				market = EthereumMarket.createOnlineEthereumMarket();
				break;
			case DOLLAR:
				market = DollarMarket.createOnlineDollarMarket();
				break;
			default:
				market = BitcoinMarket.createOnlineBitcoinMarket();
				break;
			}			
		}else{
			switch(marketEnum){
			case BITCOIN:
				market = BitcoinMarket.createOfflineBitcoinMarket();
				break;
			case ETHEREUM:
				market = EthereumMarket.createOfflineEthereumMarket();
				break;
			case DOLLAR:
				market = DollarMarket.createOfflineDollarMarket();
				break;
			default:
				market = BitcoinMarket.createOfflineBitcoinMarket();
				break;
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
