package asset;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class StockAsset implements Asset {
	
	private String assetName;
	
	private List<BigDecimal> closeList = new ArrayList<>();


	@Override
	public String getAsset() {
		return this.assetName;
	}

	@Override
	public void setCloseList() {

	}

	@Override
	public List<BigDecimal> getCloseList() {
		return null;
	}

	@Override
	public void setAsset(String assetName) {
		this.assetName = assetName;		
	}

	@Override
	public void setAllPriceList() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<?> getAllPriceList() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
