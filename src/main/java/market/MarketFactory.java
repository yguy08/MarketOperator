package market;

public class MarketFactory {
	
	public Market createMarket(String marketName) {
		if(marketName.equals(MarketsEnum.BITCOIN.getMarketName())){
			return createOnlineBitcoinMarket();
		}else if(marketName.equals(MarketsEnum.BITCOIN_OFFLINE.getMarketName())){
			return null;
		}else{
			return null;
		}
	}
	
	public static Market createOnlineBitcoinMarket(){
		return BitcoinMarket.createOnlineBitcoinMarket();
	}
	
	public static Market createOfflineBitcoinMarket(){
		return BitcoinMarket.createOfflineBitcoinMarket();
	}
	
	//create online dollar market
	
	//create offline dollar market
	
	//create online ethereum market
	
	//create offline ethereum market	
	
	
}
