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
		String sqlCommand = "SELECT Base,Counter,Exchange,Close FROM markets WHERE date = " + SpecVaultDate.getTodayMidnightEpochSeconds(Instant.now())
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
	
	public static List<Market> getLongEntries(DbConnectionEnum dbce){
		//select symbol, max close where date > date order by symbol asc
		//select symbol,current close where date > date order by symbol asc
		//for m1, m2 -> if m1 close = m2 close -> add to list
		return null;
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
