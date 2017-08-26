package com.speculation1000.specvault.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
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
		+ " ORDER BY Counter,Base,Exchange ASC";
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
	
	public static List<Market> getMaxCloseList(DbConnectionEnum dbce, int days){
		Instant instant = Instant.now().minusSeconds(86400 * days);
		String sqlCommand = "SELECT Base,Counter,Exchange,Max(Close) Close" 
				+ " FROM Markets" 
				+ " WHERE Date >= " + SpecVaultDate.getTodayMidnightEpochSeconds(instant) 
				+ " GROUP BY Base,Counter,Exchange" 
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
	
	public static List<Market> getMinCloseList(DbConnectionEnum dbce, int days){
		Instant instant = Instant.now().minusSeconds(86400 * days);
		String sqlCommand = "SELECT Base,Counter,Exchange,Min(Close) Close" 
				+ " FROM Markets" 
				+ " WHERE Date >= " + SpecVaultDate.getTodayMidnightEpochSeconds(instant) 
				+ " GROUP BY Base,Counter,Exchange" 
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
	
	public static List<Market> getCurrentCloseList(DbConnectionEnum dbce){
		Instant instant = Instant.now();
		String sqlCommand = "SELECT Base,Counter,Exchange,Max(Close) Close" 
				+ " FROM Markets" 
				+ " WHERE Date = " + SpecVaultDate.getTodayMidnightEpochSeconds(instant) 
				+ " GROUP BY Base,Counter,Exchange" 
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
	
	public static List<Market> getMarketsAtXDayHigh(DbConnectionEnum dbce,int days){
		List<Market> maxCloseList = getMaxCloseList(DbConnectionEnum.H2_MAIN,25);
		List<Market> currentClose = getCurrentCloseList(DbConnectionEnum.H2_MAIN);
		List<Market> marketsAtHighList = new ArrayList<>();
		for(int i = 0; i < maxCloseList.size();i++){
			BigDecimal maxClose = maxCloseList.get(i).getClose();
			BigDecimal current = currentClose.get(i).getClose();
			if(current.compareTo(maxClose) == 0){
				marketsAtHighList.add(currentClose.get(i));
			}					
		}
		return marketsAtHighList; 
	}
	
	public static List<Market> getMarketsAtXDayLow(DbConnectionEnum dbce,int days){
		List<Market> minCloseList = getMinCloseList(DbConnectionEnum.H2_MAIN,25);
		List<Market> currentClose = getCurrentCloseList(DbConnectionEnum.H2_MAIN);
		List<Market> marketsAtLowList = new ArrayList<>();
		for(int i = 0; i < minCloseList.size();i++){
			BigDecimal minClose = minCloseList.get(i).getClose();
			BigDecimal current = currentClose.get(i).getClose();
			if(current.compareTo(minClose) == 0){
				marketsAtLowList.add(currentClose.get(i));
			}					
		}
		return marketsAtLowList; 
	}
	
	public static void main(String[] args){
				
	}

}
