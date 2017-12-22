package speculator;

import java.math.BigDecimal;

public interface Speculator {
	
	public void setAccountBalance(BigDecimal amount);
	
	public BigDecimal getAccountBalance();
	
	public void setStartAccountBalance(BigDecimal amount);
	
	public BigDecimal getStartAccountBalance();
	
	public void setTotalReturnAmount(BigDecimal amount);
	
	public BigDecimal getTotalReturnAmount();
	
	public void setTotalReturnPercent();
	
	public BigDecimal getTotalReturnPercent();	
	
}
