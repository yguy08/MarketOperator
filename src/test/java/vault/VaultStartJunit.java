package vault;

import static org.junit.Assert.*;

import org.junit.Test;

import vault.VaultStart;

public class VaultStartJunit {
	
	static{
		VaultStart.testConnection();
	}
	
	@Test
	public void testTestConnection(){
		if(VaultStart.isConnected()){
			assertTrue(VaultStart.isConnected());
		}else{
			assertFalse(VaultStart.isConnected());
		}
	}

}
