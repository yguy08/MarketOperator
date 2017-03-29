package trade;

import java.math.BigDecimal;
import java.util.List;

import asset.Asset;
import entry.Entry;
import market.Market;
import position.Position;

public interface Trade {
	
	public static final String BACK_TEST = "Back Test";
	public static final String LIVE = "Live";
	
	public static final int ENTRY = 25;
	public static final int EXIT = 10;

	public void start(Market market, Asset asset);
	
	public Entry findEntry(List<BigDecimal> priceList);
	
	public Position openPosition(Asset asset, Entry entry);
	
	
}
