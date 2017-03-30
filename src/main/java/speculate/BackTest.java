package speculate;

import java.util.ArrayList;
import java.util.List;

import asset.Asset;
import asset.StockChartData;
import entry.Entry;
import market.Market;

public class BackTest implements Speculate {
	
	String tradeStyle;
	
	List<Entry> entryList = new ArrayList<>();
	
	public BackTest(Market market, Asset asset){
		this.tradeStyle = Speculate.BACK_TEST;
		setEntries(asset);
	}

	@Override
	public void setEntries(Asset asset) {
		for(int x = Speculate.ENTRY; x < asset.getCloseList().size();x++){
			Entry entry = new Entry(asset.getCloseList().subList(x - Speculate.ENTRY, x - 1));
			if(entry.isEntry()){
				entry.setLocation(x);
				entry.setStockChartData((StockChartData) asset.getPriceList().get(entry.getLocation()));
				entryList.add(entry);
			}
		}
	}

	@Override
	public List<Entry> getEntries() {
		return this.entryList;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(Entry e : entryList){
			sb.append(e.toString() + "\n");
		}
		
		return sb.toString();
	}
	
	
}
