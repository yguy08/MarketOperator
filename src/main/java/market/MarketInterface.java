package market;

import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.marketdata.MarketDataService;

import asset.Asset;

public interface MarketInterface {
		
	String getMarketName();
	
	void setMarketName(String marketName);
	
	public List<Asset> getAssetList();

	public void setAssetList();
	
	public void setOfflineAssetList();
	
	void setExchange(ExchangesEnum exchangeEnum);
	
	Exchange getExchange();
	
	void setMarketDataService(Exchange exchange);
	
	MarketDataService getMarketDataService();
	
}
