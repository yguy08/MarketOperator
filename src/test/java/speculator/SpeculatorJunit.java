package speculator;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import vault.Config;

public class SpeculatorJunit {	
	
	@Test
	public void testAccountBal(){
		Config.TestConfig();
		Speculator speculator = new DigitalSpeculator();
		assertEquals(Config.getAccountBalance(),speculator.getAccountBalance());
		speculator.setAccountBalance(new BigDecimal(1.00));
		assertEquals(1,speculator.getAccountBalance().compareTo(Config.getAccountBalance()));
		assertEquals(Config.getAccountBalance(),speculator.getStartAccountBalance());
		
	}

}
