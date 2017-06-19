package market;

import java.util.List;

import org.knowm.xchange.Exchange;

import asset.Asset;

public class EthereumMarket implements Market {
	
	private String marketName;
	
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAssetList() {
		// TODO Auto-generated method stub
		
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
