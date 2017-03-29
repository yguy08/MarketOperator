package position;

import java.math.BigDecimal;
import java.util.List;

import entry.Entry;

public class ShortPosition implements Position {

	BigDecimal entryPrice;
	BigDecimal avgTrueRange;
	BigDecimal exitPrice;
	BigDecimal profitLoss;
	
	int entryDate;
	int exitDate;
	
	
	public ShortPosition(Entry entry) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public BigDecimal getEntryPrice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEntryPrice() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getEntryDate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setEntryDate() {
		
	}

	@Override
	public BigDecimal getAverageTrueRange() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAverageTrueRange() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BigDecimal getExitPrice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setExitPrice() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getExitDate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setExitDate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setProfitLoss() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BigDecimal getProfitLoss() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
