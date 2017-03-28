package asset;

import java.math.BigDecimal;
import java.util.List;

public interface Asset {

	public String getAsset();
	
	public void setPriceList();
	
	public List<BigDecimal> getPriceList();

	void setAsset(String assetName);

	

}
