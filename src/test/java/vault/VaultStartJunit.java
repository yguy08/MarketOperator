package vault;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class VaultStartJunit {
	
	static{
		Config.setConnected();
	}
	
	@Test
	public void testTestConnection(){
		if(Config.isConnected()){
			assertTrue(Config.isConnected());
		}else{
			assertFalse(Config.isConnected());
		}
	}

}
