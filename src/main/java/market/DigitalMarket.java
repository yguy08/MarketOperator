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
import org.knowm.xchange.service.marketdata.MarketDataService;

import asset.Asset;
import asset.DigitalAsset;
import util.SaveToFile;
import vault.Config;
import vault.preloader.PreloaderControl;

public class DigitalMarket implements MarketInterface {
	
	//poloniex exchange from xchange library
	public Exchange exchange;
	
	private MarketDataService dataService;
	
	//market name
	private String marketName;
			
	//list of assets
	private List<Asset> assetList = new ArrayList<>();
	
	public DigitalMarket(MarketsEnum marketEnum){
		if(marketEnum == null){
			throw new IllegalArgumentException("Market not specified.");
		}
		setMarketName(marketEnum.getMarketName());
		if(Config.isConnected()){
			setExchange(Config.getExchangeEnum());
			setMarketDataService(exchange);
			setAssetList();
		}else{
			setOfflineAssetList();
		}
	}

	@Override
	public String getMarketName() {
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
		Asset asset;
		List<CurrencyPair> currencyPairs = exchange.getExchangeSymbols();
		//
		for(CurrencyPair currencyPair : currencyPairs){
			if(currencyPair.toString().endsWith(MarketsEnum.getMarketEnum(this).getCounter())){
				asset = new DigitalAsset(this, currencyPair);
				assetList.add(asset);
				PreloaderControl.updateStatus("Loading asset: " + asset.getAssetName());
			}
		}
		//
		SaveToFile.writeMarketListToFile(this, assetList);
	}

	@Override
	public void setOfflineAssetList() {
		Asset asset;
		List<String> currencyPairs;
		URL resourceUrl = MarketInterface.class.getResource(marketName + ".csv");
		try {
			currencyPairs = Files.readAllLines(Paths.get(resourceUrl.toURI()));
			for(String currencyPair : currencyPairs){
				asset = new DigitalAsset(this, new CurrencyPair(currencyPair));
				assetList.add(asset);
				PreloaderControl.updateStatus("Loading asset: " + asset.getAssetName());
			}
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setExchange(ExchangesEnum exchangeEnum) {
		switch(exchangeEnum){
		case POLONIEX:
			exchange = ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName());
			break;
		default:
			exchange = ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName());
		}
		
	}

	@Override
	public Exchange getExchange() {
		return this.exchange;
	}

	@Override
	public void setMarketDataService(Exchange exchange) {
		dataService = exchange.getMarketDataService();
	}

	@Override
	public MarketDataService getMarketDataService() {
		return dataService;
	}

}
