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
	
	void setLowList();
	
	List<BigDecimal> getLowList();
	
	void setHighList();
	
	List<BigDecimal> getHighList();
	
	void setPriceSubList(List priceList);
	
	List<?> getPriceSubList();
	
	void setCloseSubList();
	
	List getCloseSubList();	

}
