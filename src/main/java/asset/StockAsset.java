package asset;

import java.math.BigDecimal;
import java.util.List;

public class StockAsset implements Asset {
	
	private String assetName;

	@Override
	public String getAsset() {
		return this.assetName;
	}

	@Override
	public void setPriceList() {

	}

	@Override
	public List<BigDecimal> getPriceList() {
		
		return null;
	}

	@Override
	public void setAsset(String assetName) {
		this.assetName = assetName;		
	}

	

}
