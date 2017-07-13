package trade;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

import speculator.Speculator;
import util.DateUtils;
import util.StringFormatter;
import util.Tuple;
import vault.Config;
import vault.Displayable;
import vault.VaultMainControl;

public class Trade implements Displayable {
	
	Speculator speculator;
	
	Exit exit = null;
	
	Entry entry = null;
	
	String balance;
	
	private static List<Exit> winnersList;
	
	private static List<Exit> loserList;
	
	private List<Tuple<List<Entry>, List<Exit>>> entryExit = new ArrayList<>();

	public Trade(List<Exit> exitList, Speculator speculator){
		this.speculator = speculator;
		setEntryExitList(exitList);
	}
	
	public Trade(Entry entry, Speculator speculator){
		this.entry = entry;
		this.speculator = speculator;
	}

	public Trade(Exit exit, Speculator speculator){
		this.exit = exit;
		this.speculator = speculator;
		setBalance();
	}
	
	private void setBalance() {
		speculator.setAccountBalance(exit.calcGainLossAmount());
		
		if(exit.calcGainLossAmount().compareTo(new BigDecimal(0.00)) >= 0){
			winnersList.add(exit);
		}else{
			loserList.add(exit);
		}
		
		balance = StringFormatter.bigDecimalToShortString(speculator.getAccountBalance());
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

	public List<Tuple<List<Entry>, List<Exit>>> getEntryExitList() {
		return entryExit;
	}
	
	public List<Displayable> runBackTest(){
		winnersList = new ArrayList<>();
		loserList = new ArrayList<>();
		List<Entry> unitList = new ArrayList<>();
		List<Displayable> resultsList = new ArrayList<>();
		Random RANDOM = new Random();
		for(Tuple<List<Entry>,List<Exit>> entryExit : entryExit){
			for(Exit exit : entryExit.b){
				if(unitList.contains(exit.getEntry()) && !(exit.isOpen())){
					unitList.remove(exit.getEntry());					
					resultsList.add(new Trade(exit, speculator));
					System.out.println("New Exit! " + exit.toString());
				}else if(unitList.contains(exit.getEntry()) && exit.isOpen()){
					resultsList.add(new Trade(exit, speculator));
					System.out.println("Still Open! " + exit.toString());
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
				if(unitList.size() >= Config.getMaxUnits()){
					break;
				}else if(Config.isSortVol()){
					unitList.add(entry);
					System.out.println(entry.toString());
					resultsList.add(new Trade(entry, speculator));
				}else{
					//can add ones twice...
					int r = RANDOM.nextInt(entryExit.a.size());
					unitList.add(entryExit.a.get(r));
					System.out.println(entryExit.a.get(r).toString());
					resultsList.add(new Trade(entryExit.a.get(r), speculator));
				}
			}
		}
		calcWinnersAndLosers();
		return resultsList;
	}
	
	private void calcWinnersAndLosers(){
		BigDecimal winnerSum = new BigDecimal(0.00);
		BigDecimal loserSum = new BigDecimal(0.00);
		//
		for(Exit exit : winnersList){
			winnerSum = winnerSum.add(exit.calcGainLossAmount());
		}
		//
		for(Exit exit : loserList){
			loserSum = loserSum.add(exit.calcGainLossAmount());
		}
		//
		BigDecimal avgWinner = winnerSum.divide(new BigDecimal(winnersList.size()), MathContext.DECIMAL32);
		BigDecimal avgLoser = loserSum.divide(new BigDecimal(loserList.size()), MathContext.DECIMAL32);
		Double winPercent = new Double(winnersList.size())/new Double(loserList.size()) * 100;
		
		StringBuilder sb = new StringBuilder();
		sb.append("Wins: " + winnersList.size() + " Avg Win: " + StringFormatter.prettyPointX(avgWinner));
		sb.append(" Losses: " + loserList.size() + " Avg Loss: " + StringFormatter.prettyPointX(avgLoser));
		sb.append(" Win %: " + winPercent.intValue() + "%");
		VaultMainControl.getVaultMainControl().setStatus(sb.toString());
	}
	
	@Override
	public String toString(){
		if(exit == null && entry != null){
			return entry.toString();
		}else if (exit!=null){
			if(exit.isExit){
				return exit.toString() + " ((" + balance + "))";
			}else if(exit.isOpen){
				return exit.toString() + " ((" + balance + "))"; 
			}else{
				return "Error!";
			}
		}else{
			return "Error!";
		}
		
	}
	
}
