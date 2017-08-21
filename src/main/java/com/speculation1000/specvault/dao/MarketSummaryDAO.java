package com.speculation1000.specvault.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.logging.Level;

import com.speculation1000.specvault.db.DbConnection;
import com.speculation1000.specvault.db.DbConnectionEnum;
import com.speculation1000.specvault.db.QueryTable;
import com.speculation1000.specvault.log.SpecDbLogger;
import com.speculation1000.specvault.market.Market;
import com.speculation1000.specvault.time.SpecVaultDate;

public class MarketSummaryDAO {
	
	private static final SpecDbLogger specLogger = SpecDbLogger.getSpecDbLogger();
	
	public static List<Market> getLatestTicker(DbConnectionEnum dbce){
		String sqlCommand = "SELECT * FROM markets WHERE date = " + SpecVaultDate.getTodayMidnightEpochSeconds(Instant.now())
		+ " ORDER BY Counter,Base ASC";
		Connection conn = DbConnection.connect(dbce);
		List<Market> marketList = QueryTable.genericMarketQuery(conn, sqlCommand);
		try{
			conn.close();
		}catch(SQLException e){
			while (e != null) {
            	specLogger.logp(Level.INFO, QueryTable.class.getName(), "getLongEntries", e.getMessage());
	            e = e.getNextException();
	        }
		}
		return marketList;
	}
	
	//use timeframes
	public static List<Market> getRecentMarketHistory(DbConnectionEnum dbce){
		Instant instant = Instant.now().minusSeconds(86400 * 55);
		String sqlCommand = "SELECT * FROM markets WHERE date >= " + SpecVaultDate.getTodayMidnightEpochSeconds(instant)
		+ " ORDER BY Counter,Base ASC";
		Connection conn = DbConnection.connect(dbce);
		List<Market> marketList = QueryTable.genericMarketQuery(conn, sqlCommand);
		try{
			conn.close();
		}catch(SQLException e){
			while (e != null) {
            	specLogger.logp(Level.INFO, QueryTable.class.getName(), "getLongEntries", e.getMessage());
	            e = e.getNextException();
	        }
		}
		return marketList;
	}
	
	public static void main(String[] args){
		//THIS!!!
		Instant now = Instant.now();
		List<Market> marketList = getLatestTicker(DbConnectionEnum.H2_MAIN);
		Instant end = Instant.now();
		for(Market market : marketList){
			System.out.println(market.toString());
		}
		System.out.println("total time taken: " + String.valueOf(end.getEpochSecond() - now.getEpochSecond()));
	}

}
