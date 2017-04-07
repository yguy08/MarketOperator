package speculate;

import java.math.BigDecimal;

import asset.Asset;
import market.Market;

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
	
	/**
	 * 
	 * @param market
	 */
	void backTestAllAssetsSingleMarket(Market market);
	
	void backTestSingleAsset(Market market, Asset asset);
	
}
