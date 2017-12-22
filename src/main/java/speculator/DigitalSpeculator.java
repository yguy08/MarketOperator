package speculator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import vault.Config;

public class DigitalSpeculator implements Speculator {
	
	//account balance b/c every speculator has an account
	private BigDecimal accountBalance = new BigDecimal(0.00);
	
	//start account balance
	private BigDecimal startAccountBalance = new BigDecimal(0.00);
	
	//total return amount because every speculator can calculate end - start balance
	private BigDecimal totalReturnAmount = new BigDecimal(0.00);
	
	//total return percent because every speculator has a total return %
	private BigDecimal totalReturnPercent = new BigDecimal(0.00);

	public DigitalSpeculator(){
		setStartAccountBalance(Config.getAccountBalance());
		setAccountBalance(Config.getAccountBalance());
	}

	@Override
	public void setAccountBalance(BigDecimal amount){
		accountBalance = accountBalance.add(amount);
	}
	
	@Override
	public BigDecimal getAccountBalance(){
		return accountBalance;
	}
	
	@Override
	public void setStartAccountBalance(BigDecimal amount) {
		startAccountBalance = amount;		
	}

	@Override
	public BigDecimal getStartAccountBalance() {
		return startAccountBalance;
	}
	
	@Override
	public void setTotalReturnAmount(BigDecimal amount){
		totalReturnAmount = amount;
	}
	
	@Override
	public BigDecimal getTotalReturnAmount(){
		return totalReturnAmount;
	}
	
	@Override
	public void setTotalReturnPercent(){
		totalReturnPercent = accountBalance.subtract(startAccountBalance, MathContext.DECIMAL32)
				.divide(startAccountBalance, MathContext.DECIMAL32)
				.setScale(2, RoundingMode.HALF_DOWN)
				.multiply(new BigDecimal(100.00), MathContext.DECIMAL32);
	}
	
	@Override
	public BigDecimal getTotalReturnPercent() {
		return totalReturnPercent;
	}

	@Override
	public String toString(){
		return "Speculator";
	}
	
}
