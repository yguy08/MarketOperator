package trade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

import price.Entry;
import price.Exit;
import price.PriceData;
import speculator.Speculator;
import util.DateUtils;
import util.Tuple;

public class Trade {
	
	Speculator speculator;
	
	List<Tuple<List<Entry>, List<Exit>>> entryExit = new ArrayList<>();

	public Trade(Speculator speculator){
		this.speculator = speculator;
	}

	public void setEntryExitList(List<Exit> exitList) {
		Date startDate = exitList.get(0).getEntryDate();
		int days = DateUtils.getNumDaysFromDateToToday(startDate);
		for(int z = 0; z <= days; z++){
			List<Entry> entries = new ArrayList<>();
			List<Exit> exits = new ArrayList<>();
			Date currentDateOfBacktest = DateUtils.addDays(startDate, z);
			for(Exit exit : exitList){
				Date closeDate = exit.getDateTime();
				if(closeDate.equals(currentDateOfBacktest)){
					exits.add(exit);
				}
				
				Date entryDate = exit.getEntryDate();
				if(entryDate.equals(currentDateOfBacktest)){
					entries.add(exit.getEntry());
				}
			}
			entryExit.add(new Tuple<List<Entry>, List<Exit>>(entries,exits));
		}
	}
	
	public List<String> runBackTest(){
		List<Entry> unitList = new ArrayList<>();
		List<String> resultsList = new ArrayList<>();
		Random RANDOM = new Random();
		for(Tuple<List<Entry>,List<Exit>> entryExit : entryExit){
			for(Exit exit : entryExit.b){
				if(unitList.contains(exit.getEntry()) && !(exit.isOpen())){
					unitList.remove(exit.getEntry());
					speculator.setAccountBalance(exit.calcGainLossAmount());
					resultsList.add(exit.toString() + " " + speculator.getAccountBalance());
					System.out.println("New Exit! " + exit.toString());
				}
			}
			
			Collections.sort(entryExit.a, new Comparator<Entry>() {
			    @Override
				public int compare(Entry o1, Entry o2) {
			        return o2.getVolume().compareTo(o1.getVolume());
			    }
			});
			
			//for entry e : entry exit a...
			for(Entry entry : entryExit.a){
				if(unitList.size() > speculator.getMaxUnits()){
					break;
				}else if(speculator.isSortVol()){
					unitList.add(entry);
					System.out.println(entry.toString());
					resultsList.add(entry.toString());
				}else{
					int r = RANDOM.nextInt(entryExit.a.size());
					unitList.add(entryExit.a.get(r));
					System.out.println(entryExit.a.get(r).toString());
					resultsList.add(entryExit.a.get(r).toString());
				}
			}
		}
		
		return resultsList;
	}

	public List<Tuple<List<Entry>, List<Exit>>> getEntryExit() {
		return entryExit;
	}
	
}
