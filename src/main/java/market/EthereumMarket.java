package market;

import java.util.ArrayList;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;

import asset.Asset;
import asset.AssetFactory;

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
		return null;
	}

	@Override
	public void setAssetList() {
		Asset asset;
		List<CurrencyPair> currencyPairs = exchange.getExchangeSymbols();
		for(CurrencyPair currencyPair : currencyPairs){
			if(currencyPair.toString().endsWith("BTC")){
				asset = AssetFactory.createAsset(this, currencyPair.toString());
				assetList.add(asset);
			}
		}		
	}

	@Override
	public void setOfflineAssetList() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setExchange() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Exchange getExchange() {
		// TODO Auto-generated method stub
		return null;
	}

}
