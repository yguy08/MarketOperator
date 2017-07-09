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
import util.SaveToFile;

public class DollarMarket implements Market {
	
	//poloniex exchange from xchange library
	public Exchange exchange;
	
	private String marketName;
	
	//list of assets
	private List<Asset> assetList = new ArrayList<>();
	
	//static factory method to create online digital market
	public static DollarMarket createOnlineDollarMarket(){
		DollarMarket dollarMarket = new DollarMarket();
		dollarMarket.setExchange();
		dollarMarket.setMarketName(MarketsEnum.DOLLAR.getMarketName());
		dollarMarket.setAssetList();
		return dollarMarket;
	}
	
	//static factory method to create offline digital market
	public static DollarMarket createOfflineDollarMarket(){
		DollarMarket dollarMarket = new DollarMarket();
		dollarMarket.setMarketName(MarketsEnum.DOLLAR_OFFLINE.getMarketName());
		dollarMarket.setOfflineAssetList();
		return dollarMarket;
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
		return assetList;
	}

	@Override
	public void setAssetList() {
		Asset asset;
		List<CurrencyPair> currencyPairs = exchange.getExchangeSymbols();
		for(CurrencyPair currencyPair : currencyPairs){
			if(currencyPair.toString().endsWith("USDT")){
				asset = AssetFactory.createAsset(this,currencyPair.toString());
				assetList.add(asset);
			}
		}
		SaveToFile.writeMarketListToFile(this, assetList);
	}

	@Override
	public void setOfflineAssetList() {
		Asset asset;
		List<String> currencyPairs;
		URL resourceUrl = getClass().getResource(MarketsEnum.DOLLAR + ".csv");
		try {
			currencyPairs = Files.readAllLines(Paths.get(resourceUrl.toURI()));
			for(String currencyPair : currencyPairs){
				asset = AssetFactory.createAsset(this,currencyPair);
				assetList.add(asset);
			}
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setExchange() {
		exchange = ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName());
	}

	@Override
	public Exchange getExchange() {
		return exchange;
	}


}
