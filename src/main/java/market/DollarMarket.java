package market;

import java.util.List;

import org.knowm.xchange.Exchange;

import asset.Asset;

public class DollarMarket implements Market {
	
	//market name
	private String marketName;
	
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
		return null;
	}

	@Override
	public void setAssetList() {		

	}

	@Override
	public void setOfflineAssetList() {		

	}

	@Override
	public void setExchange() {		

	}

	@Override
	public Exchange getExchange() {
		return null;
	}

}
