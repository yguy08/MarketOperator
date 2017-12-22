package market;

public enum MarketsEnum {
	
	BITCOIN 			("Bitcoin","BTC"),
	BITCOIN_OFFLINE		("Bitcoin Offline","BTC"),
	DOLLAR 				("Dollar","USDT"),
	DOLLAR_OFFLINE		("Dollar Offline","USDT"),
	ETHEREUM 			("Ethereum","ETH"),
	ETHEREUM_OFFLINE	("Ethereum Offline","ETH");	
	
	private String marketName;
	private String counter;
	
	MarketsEnum(String marketName, String counter){
		this.marketName = marketName;
		this.counter = counter;
	}
	
	public String getMarketName(){
		return marketName;
	}
	
	public String getCounter(){
		return counter;
	}
	
	public static MarketsEnum getMarketEnum(MarketInterface market){
		if(market == null){
			throw new IllegalArgumentException("No market specified");
		}
		
		if(market.getMarketName().equalsIgnoreCase(BITCOIN.getMarketName())){
			return BITCOIN;
		}else if(market.getMarketName().equalsIgnoreCase(ETHEREUM.getMarketName())){
			return ETHEREUM;
		}else{
			return DOLLAR;
		}
	}

}
