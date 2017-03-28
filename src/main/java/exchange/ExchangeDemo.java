package exchange;

import market.Market;

public class ExchangeDemo {

	public static void main(String[] args) {
		
		ExchangeFactory exchangeFactory = new ExchangeFactory();
		
		Exchange exchange = exchangeFactory.createExchange(Market.STOCK_MARKET);
		
		System.out.println(exchange.getName());

	}

}
