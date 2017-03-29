package asset;

import java.util.List;

public interface Asset {

	String getAsset();
	
	void setAsset(String assetName);
	
	void setPriceList();
	
	List<?> getPriceList();
	
	List<?> getCloseList();
	
	void setCloseList();	

	
	

}
