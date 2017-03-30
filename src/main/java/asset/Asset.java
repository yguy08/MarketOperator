package asset;

import java.math.BigDecimal;
import java.util.List;

public interface Asset {

	void setAsset(String assetName);
	
	String getAsset();
	
	void setPriceList(String assetName);
	
	List<?> getPriceList();
	
	void setCloseList();
	
	List<BigDecimal> getCloseList();
	
	void setPriceSubList(List priceList);
	
	List<?> getPriceSubList();
	
	void setCloseSubList();
	
	List getCloseSubList();
	

}
