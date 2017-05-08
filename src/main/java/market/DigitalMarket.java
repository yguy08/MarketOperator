package market;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.poloniex.PoloniexExchange;

import asset.Asset;
import asset.AssetFactory;

public class DigitalMarket implements Market {
	
	//poloniex exchange from xchange library
	//public static final Exchange exchange = ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName());
	
	//market name
	private String marketName;
		
	//list of assets
	private List<Asset> assetList = new ArrayList<>();
	
	//static factory method to create online digital market
	public static DigitalMarket createOnlineDigitalMarket(){
		DigitalMarket digitalMarket = new DigitalMarket();
		digitalMarket.setMarketName(Market.DIGITAL_MARKET);
		digitalMarket.setAssetList();
		return digitalMarket;
	}
	
	//static factory method to create offline digital market
	public static DigitalMarket createOfflineDigitalMarket(){
		DigitalMarket digitalMarket = new DigitalMarket();
		digitalMarket.setMarketName(Market.DIGITAL_OFFLINE);
		digitalMarket.setOfflineAssetList();
		return digitalMarket;
	}
	
	@Override
	public String getMarketName() {
		return marketName;
	}
	
	@Override
	public String toString(){
		return marketName;
	}

	@Override
	public void setMarketName(String marketName) {
		this.marketName = marketName;		
	}

	@Override
	public List<Asset> getAssetList() {
		return this.assetList;
	}

	@Override
	public void setAssetList() {
		AssetFactory aFactory = new AssetFactory();
		Asset asset = null;
		List<CurrencyPair> currencyPairs = exchange.getExchangeSymbols();
		for(CurrencyPair currencyPair : currencyPairs){
			if(currencyPair.toString().endsWith("BTC")){
				asset = aFactory.createAsset(this, currencyPair.toString());
				assetList.add(asset);
			}
		}
	}
	
	@Override
	public void setOfflineAssetList(){
		AssetFactory aFactory = new AssetFactory();
		Asset asset = null;
		List<String> currencyPairs;
		URL resourceUrl = getClass().getResource("MarketList.csv");
		try {
			currencyPairs = Files.readAllLines(Paths.get(resourceUrl.toURI()));
			for(String currencyPair : currencyPairs){
				if(currencyPair.endsWith("BTC")){
					asset = aFactory.createAsset(this, currencyPair);
					assetList.add(asset);
				}
			}
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
		
	}
	

}
