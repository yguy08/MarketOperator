package asset;

import java.util.List;

public interface Asset {

	void setAsset(String assetName);
	
	String getAsset();
	
	void setPriceList(String assetName);
	
	List getPriceList();
	

}
