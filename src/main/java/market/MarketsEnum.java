package market;

public enum MarketsEnum {
	
	BITCOIN 			("Bitcoin"),
	BITCOIN_OFFLINE		("Bitcoin Offline"),
	DOLLAR 				("Dollar"),
	DOLLAR_OFFLINE		("Dollar Offline"),
	ETHEREUM 			("Ethereum"),
	ETHEREUM_OFFLINE	("Ethereum Offline");	
	
	private String marketName;
	
	MarketsEnum(String marketName){
		this.marketName = marketName;
	}
	
	public String getMarketName(){
		return marketName;
	}

}
