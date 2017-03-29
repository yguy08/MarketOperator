package asset;

import java.math.BigDecimal;
import java.util.List;

public interface Asset {

	public String getAsset();
	
	public void setCloseList();
	
	public List<BigDecimal> getCloseList();

	void setAsset(String assetName);
	
	public void setAllPriceList();
	
	public List<?> getAllPriceList();
	

}
