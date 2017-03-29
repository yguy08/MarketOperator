package trade;

import java.math.BigDecimal;
import java.util.List;

import asset.Asset;
import entry.Entry;
import market.Market;
import position.Position;

public class Live implements Trade {

	public Live(Market market, Asset asset) {
		
	}

	@Override
	public void start(Market market, Asset asset) {
		
	}

	@Override
	public Entry findEntry(List<BigDecimal> priceList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Position openPosition(Asset asset, Entry entry) {
		return null;
		// TODO Auto-generated method stub
	}
	
	

}
