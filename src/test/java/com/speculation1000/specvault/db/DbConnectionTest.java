package com.speculation1000.specvault.db;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;

import org.junit.Test;

import com.speculation1000.specvault.log.SpecDbLogger;

public class DbConnectionTest {
	
	private static final SpecDbLogger specLogger = SpecDbLogger.getSpecDbLogger();

	@Test
	public void testH2MainConnection(){
		Connection conn = DbConnection.connect(DbConnectionEnum.H2_MAIN);
		assertNotNull(conn);
		
		try{
			conn.close();
		}catch(SQLException e){
			specLogger.logp(Level.SEVERE, DbConnectionTest.class.getName(), "testH2MainConnection", e.getMessage());
		}
	}


}
