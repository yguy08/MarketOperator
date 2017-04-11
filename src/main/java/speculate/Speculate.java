package speculate;

import java.math.BigDecimal;
import java.util.List;

import asset.Asset;
import entry.Entry;
import javafx.collections.ObservableList;
import market.Market;
import position.Position;

public interface Speculate {

	String BACK_TEST = "Back Test";
	String LIVE = "Live";
	
	String LONG = "Long";
	String SHORT = "Short";
	
	BigDecimal STOCK_EQUITY = new BigDecimal(10_000.00); 
	BigDecimal DIGITAL_EQUITY = new BigDecimal(3.00000000);
	
	BigDecimal RISK = new BigDecimal(0.01);
	BigDecimal STOP = new BigDecimal(2.00);
	
	int ENTRY = 25;
	int EXIT  = 10;
	int MOVING_AVG = 20;
	
	void setAccountEquity(BigDecimal tradeResult);
	
	BigDecimal getAccountEquity();
	
	void setTotalReturnPercent();
	
	BigDecimal getTotalReturnPercent();
	
	void setEntryList(Entry entry);
	
	List<Entry> getEntryList();
	
	void setSortedEntryList(List<Entry> entryList);
	
	List<Entry> getSortedEntryList();
	
	void setPositionList(Position position);
	
	List<Position> getPositionList();
	
	void setSortedPositionList(List<Position> positionList);
	
	List<Position> getSortedPositionList();
	
	Speculate newNewBackTest(Market market, Speculate speculate);
	
	public Market getMarket();
	
	void addUnit();
	
	void subtractUnit();
	
	/**
	 * Get the last entry for all assets in a single market
	 * May or may not still be open   
	 * @param market
	 */
	void getLastEntrySingleMarket(Market market);
	
	/**
	 * Get the last position for all assets in a single market
	 * May or may not still be open, marked true or false
	 * @param market
	 */
	void getLastPositionSingleMarket(Market market);
	
	/**
	 * Get the entries for today
	 * Can open these right away
	 * @param market
	 */
	void getCurrentEntriesSingleMarket(Market market);
	
	/**
	 * Get all open positions for a single market
	 * @param market
	 */
	void getAllOpenPositionsSingleMarket(Market market);
	
	/**
	 * Get current positions to close
	 */
	void getPositionsToCloseSingleMarket(Market market);
	
	public void getAllOpenPositionsWithEntry(Market market, ObservableList<String> obsList);
	
	void getPositionsToCloseSingleMarket(Market market, ObservableList<String> obsList);
	
	void backTest(Market market, ObservableList<String> obsList);
	
	void newBackTest(Market market, Speculate speculate, ObservableList<String> obsList);
	
	void setBuyEntryList(Entry entry);
	
	List<Entry> getBuyEntryList();
	
	void setBuyPositionList(Position position);
	
	List<Entry> getBuyPositionList();
	
	/**
	 * 
	 * @param market
	 */
	void backTestAllAssetsSingleMarket(Market market);
	
	void backTestSingleAsset(Market market, Asset asset);

	int getUnit();
	
}
