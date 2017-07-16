package market;

import org.knowm.xchange.poloniex.PoloniexExchange;

public enum ExchangesEnum {
	
	POLONIEX 			("PoloniexExchange");	
	
	private String exchangeName;
	
	ExchangesEnum(String exchangeName){
		this.exchangeName = exchangeName;
	}
	
	public String getExchangeName(){
		return exchangeName;
	}
	
	public String getClassName(ExchangesEnum exchangeEnum){
		switch (exchangeEnum){
		case POLONIEX:
			return PoloniexExchange.class.getName();
		default:
			return PoloniexExchange.class.getName();
		}
	}

}
