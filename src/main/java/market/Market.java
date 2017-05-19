package market;

import java.util.List;
import org.knowm.xchange.Exchange;
import asset.Asset;

public interface Market {
		
	String getMarketName();
	
	void setMarketName(String marketName);
	
	public List<Asset> getAssetList();

	public void setAssetList();
	
	public void setOfflineAssetList();
	
	void setExchange();
	
	Exchange getExchange();
	
}
