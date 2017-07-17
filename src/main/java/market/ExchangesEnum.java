package market;

import org.knowm.xchange.poloniex.PoloniexExchange;

public enum ExchangesEnum {
	
	POLONIEX("Poloniex", PoloniexExchange.class.getName());
	
	private String exchangeName;
	private String className;
	
	ExchangesEnum(String exchangeName, String className){
		this.exchangeName = exchangeName;
		this.className = className;
	}
	
	public String getExchangeName(){
		return exchangeName;
	}
	
	public String getClassName(){
		return className;
	}

}
