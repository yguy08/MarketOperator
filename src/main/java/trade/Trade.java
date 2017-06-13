package trade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import entry.Entry;
import exit.Exit;
import speculator.Speculator;
import util.DateUtils;

public class Trade {
	
	Entry entry;
	
	Exit exit;
	
	Speculator speculator;

	public Trade(Speculator speculator){
		this.entry = entry;
		this.exit = exit;
		this.speculator = speculator;
	}
	
	
	
	public static List<String> runBackTest(List<Exit> exitList, Speculator speculator){
		Date startDate = exitList.get(0).getEntryDate();
		int days = DateUtils.getNumDaysFromDateToToday(startDate);
		List<Entry> unitList = new ArrayList<>();
		List<String> resultsList = new ArrayList<>();
		int maxUnits = speculator.getMaxUnits();
		for(int z = 0; z <= days; z++){
			Date currentDateOfBacktest = DateUtils.addDays(startDate, z);
			for(Exit exit : exitList){
				Date closeDate = exit.getDateTime();
				if(closeDate.equals(currentDateOfBacktest)){
					for(int k = 0; k < unitList.size();k++){
						if(exit.getEntry().equals(unitList.get(k)) && exit.isExit()){
							unitList.remove(unitList.get(k));
							resultsList.add("Exit: " + exit.toString());
							System.out.println("New Exit " + exit.toString());
						}else if (exit.getEntry().equals(unitList.get(k)) && exit.isOpen()){
							resultsList.add("Open: " + exit.toString());
							System.out.println("Still Open " + exit.toString());
						}
					}					
				}
				
				if(exit.getEntryDate().equals(currentDateOfBacktest) && unitList.size() <= maxUnits){
					unitList.add(exit.getEntry());
					System.out.println("New Entry " + exit.getEntry().toString());
					resultsList.add("New Entry " + exit.getEntry().toString());
				}
			}
		}		
		return resultsList;
	}
	
}
