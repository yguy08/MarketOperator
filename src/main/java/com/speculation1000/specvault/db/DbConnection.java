package com.speculation1000.specvault.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

import com.speculation1000.specvault.log.SpecDbLogger;

public class DbConnection {
	
	private static final SpecDbLogger specLogger = SpecDbLogger.getSpecDbLogger();
	
	public static Connection connect(DbConnectionEnum dbce){
		Connection conn = null;
        try {
        	Class.forName(dbce.getClassForName());
            conn = DriverManager.getConnection(dbce.getConnectionString());
            specLogger.logp(Level.INFO, DbConnection.class.getName(), "connect", "Connection to " + dbce.getConnectionString() + " established");
        } catch (SQLException ex) {
        	while (ex != null) {
            	specLogger.logp(Level.INFO, DbConnection.class.getName(), "connect", "SQLException: " + ex.getMessage());
	            ex = ex.getNextException();
	        }
	        throw new RuntimeException("Error");
        } catch (ClassNotFoundException e) {
        	specLogger.logp(Level.INFO, DbConnection.class.getName(), "connect", "ClassNotFoundException: " + e.getMessage());
		}
        return conn;
	}

}
