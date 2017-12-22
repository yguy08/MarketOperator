package trade;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import asset.Asset;
import market.MarketInterface;
import speculator.DigitalSpeculator;
import speculator.Speculator;
import util.DateUtils;
import util.Tuple;
import vault.Config;

public class TradeJunit {
	
	static{
		Config.TestConfig();
	}
	
	MarketInterface market = Config.getMarket();
	
	@Test
	public void testEntriesHighestVol(){
		Speculator speculator = new DigitalSpeculator();
		List<Exit> exitList = new ArrayList<>();
		for(Asset a : Config.getMarket().getAssetList()){
			exitList.addAll(a.getEntryStatusList(speculator));
		}		
		//sort list
		Collections.sort(exitList, new Comparator<Exit>() {
		    @Override
			public int compare(Exit exit1, Exit exit2) {
		        return exit1.getEntryDate().compareTo(exit2.getEntryDate());
		    }
		});		
		Trade t = new Trade(exitList, speculator);
		for(Tuple<List<Entry>,List<Exit>> entryExit : t.getEntryExitList()){
			if(entryExit.a.size()>0){
				Collections.sort(entryExit.a, new Comparator<Entry>() {
				    @Override
					public int compare(Entry o1, Entry o2) {
				        return o2.getVolume().compareTo(o1.getVolume());
				    }
				});			
				List<BigDecimal> volList = new ArrayList<>();
				for(Entry entry : entryExit.a){
					volList.add(entry.getVolume());
				}			
				BigDecimal max = Collections.max(volList);
				BigDecimal min = Collections.min(volList);
				assertEquals(max, entryExit.a.get(0).getVolume());
				assertEquals(min, entryExit.a.get(entryExit.a.size()-1).getVolume());
			}
		}
		
	}
	
	@Test
	public void testFirstEntryIsOldest(){
		Speculator speculator = new DigitalSpeculator();
		List<Exit> exitList = new ArrayList<>();
		for(Asset a : market.getAssetList()){
			exitList.addAll(a.getEntryStatusList(speculator));
		}		
		//sort list
		Collections.sort(exitList, new Comparator<Exit>() {
		    @Override
			public int compare(Exit exit1, Exit exit2) {
		        return exit1.getEntryDate().compareTo(exit2.getEntryDate());
		    }
		});		
		Trade t = new Trade(exitList, speculator);
		Date startDate = exitList.get(0).getEntryDate();
		int days = DateUtils.getNumDaysFromDateToToday(startDate);
		for(Tuple<List<Entry>,List<Exit>> entryExit : t.getEntryExitList()){
			for(Entry entry : entryExit.a){
				Date nextDate = entry.getDateTime();
				int nextDateDays = DateUtils.getNumDaysFromDateToToday(nextDate);
				boolean isEarlier = nextDateDays > days ? true : false;
				System.out.println("Start: " + DateUtils.dateToMMddFormat(startDate) + " Current: " + DateUtils.dateToMMddFormat(nextDate));
				assertEquals(false, isEarlier);
			}
		}
		
	}

}
