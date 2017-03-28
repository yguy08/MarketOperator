package bitcoin;

import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.poloniex.PoloniexExchange;
import org.knowm.xchange.service.marketdata.MarketDataService;

import operator.Market;

public class PoloMarket {
		
		public Exchange poloniex;
		public MarketDataService dataService;
		public List<CurrencyPair> assetList;

		public PoloMarket(){
			poloniex 	= ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName());
			dataService = poloniex.getMarketDataService();
			assetList 	= poloniex.getExchangeSymbols();
		}

	}
