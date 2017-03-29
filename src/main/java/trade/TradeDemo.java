package trade;

import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;

import asset.Asset;
import asset.AssetFactory;
import market.Market;
import market.MarketFactory;

public class TradeDemo {

	public static void main(String[] args) {

		MarketFactory marketFactory = new MarketFactory();
		
		Market market = marketFactory.createMarket(Market.DIGITAL_MARKET);
		
		AssetFactory assetFactory = new AssetFactory();
		
		Asset asset = assetFactory.createAsset(market);
		
		asset.setAsset("XMR/BTC");
		asset.setCloseList();
		asset.setAllPriceList();
		
		for(int x = 0; x < asset.getAllPriceList().size();x++){
			System.out.println(((PoloniexChartData) asset.getAllPriceList().get(x)).getClose());
		}
		
		//TradeFactory tradeFactory = new TradeFactory();
		
		//Trade trade = tradeFactory.startTrading(Trade.BACK_TEST, market, asset);
		
		
		
		
		
	}

}
