package trade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import asset.Asset;
import entry.Entry;
import market.Market;
import position.Position;
import position.PositionFactory;

public class BackTest implements Trade {
	
	public BackTest(Market market, Asset asset) {
		start(market, asset);
	}

	@Override
	public void start(Market market, Asset asset) {
		List<BigDecimal> priceList = new ArrayList<>();
		for(int x = Trade.ENTRY; x < asset.getCloseList().size();x++){
			priceList = asset.getCloseList().subList(x - 20, x);
			Entry entry = findEntry(priceList);
			if(entry.isEntry()){
				entry.setLocation(x);
				Position position = openPosition(asset, entry);
			}else{
				
			}
		}
	}

	@Override
	public Entry findEntry(List<BigDecimal> priceList) {
		Entry entry = new Entry(priceList);
		return entry;
	}

	@Override
	public Position openPosition(Asset asset, Entry entry) {
		PositionFactory positionFactory = new PositionFactory();
		Position position = positionFactory.createPosition(entry);
		return position;
	}

}
