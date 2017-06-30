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

public class EthereumMarket implements Market {
	
	//poloniex exchange from xchange library
	public Exchange exchange;
	
	private String marketName;
	
	//list of assets
	private List<Asset> assetList = new ArrayList<>();
	
	//static factory method to create online digital market
	public static EthereumMarket createOnlineEthereumMarket(){
		EthereumMarket ethereumMarket = new EthereumMarket();
		ethereumMarket.setExchange();
		ethereumMarket.setMarketName(MarketsEnum.ETHEREUM.getMarketName());
		ethereumMarket.setAssetList();
		return ethereumMarket;
	}
	
	//static factory method to create offline digital market
	public static EthereumMarket createOfflineEthereumMarket(){
		EthereumMarket ethereumMarket = new EthereumMarket();
		ethereumMarket.setMarketName(MarketsEnum.ETHEREUM_OFFLINE.getMarketName());
		ethereumMarket.setOfflineAssetList();
		return ethereumMarket;
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
			if(currencyPair.toString().endsWith("ETH")){
				asset = AssetFactory.createAsset(this, currencyPair.toString());
				assetList.add(asset);
			}
		}
		SaveToFile.writeMarketListToFile((Market)this, assetList);
	}

	@Override
	public void setOfflineAssetList() {
		Asset asset;
		List<String> currencyPairs;
		URL resourceUrl = getClass().getResource(MarketsEnum.ETHEREUM + ".csv");
		try {
			currencyPairs = Files.readAllLines(Paths.get(resourceUrl.toURI()));
			for(String currencyPair : currencyPairs){
				asset = AssetFactory.createAsset(this, currencyPair);
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
