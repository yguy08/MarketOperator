package market;

public enum ExchangesEnum {
	
	POLONIEX 			("Poloniex");	
	
	private String exchangeName;
	
	ExchangesEnum(String exchangeName){
		this.exchangeName = exchangeName;
	}
	
	public String getExchangeName(){
		return exchangeName;
	}

}
