package entry;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import asset.Asset;
import speculator.Speculator;

public class BitcoinEntry implements Entry {
	
	List<Integer> entryIndexList = new ArrayList<>();
	
	public static BitcoinEntry createBitcoinEntry(Asset asset, Speculator speculator) {
		BitcoinEntry bEntry = new BitcoinEntry();
		bEntry.findAllEntries(asset, speculator);
		return bEntry;
	}

	@Override
	public List<Integer> getEntryIndex() {
		return entryIndexList;
	}

	@Override
	public void setEntry() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getCurrentPrice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMaxPrice(List<?> priceSubList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BigDecimal getMaxPrice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMinPrice(List<?> priceSubList) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BigDecimal getMinPrice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDirection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDirection(String direction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLocationAsIndex() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getLocationIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Boolean isEntry() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPriceSubList() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTrueRange() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTrueRange(BigDecimal trueRange) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BigDecimal getTrueRange() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStop(BigDecimal stop) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCurrentPrice(BigDecimal currentPrice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BigDecimal getStop() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUnitSize(Speculator speculator) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BigDecimal getUnitSize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOrderTotal() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BigDecimal getOrderTotal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getDateTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getVolume() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isLong() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Entry copy(Entry entry, Speculator speculator) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAssetName(String assetName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getAssetName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setVolume(BigDecimal volume) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDate(String date) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void findAllEntries(Asset asset, Speculator speculator) {
		// TODO Auto-generated method stub
		
	}
	
	

}
