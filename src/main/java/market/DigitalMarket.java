package market;

import java.util.ArrayList;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.poloniex.PoloniexExchange;

import asset.Asset;
import asset.AssetFactory;
import util.SaveToFile;

public class DigitalMarket implements Market {
	
	//consider changing to bitcoin market, then have ETH, XMR and other curriencies as markets...
	
	public static final Exchange exchange = ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName());
	
	//market name
	private String marketName = Market.DIGITAL_MARKET;
		
	//list of assets
	private List<Asset> assetList = new ArrayList<>();
	
	public DigitalMarket(){
		setAssetList();
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
	

}
