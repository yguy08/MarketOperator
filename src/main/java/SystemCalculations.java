import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;
import org.knowm.xchange.utils.DateUtils;

public class SystemCalculations {
	
	static List<BigDecimal> myList = new ArrayList<>();
		
	public static void highFinder(List<PoloniexChartData> priceList, int high){
		boolean isOpen = false;
		BigDecimal currentDay;
		BigDecimal previousDay;
		int count = 0;
		for(int x = high; x < priceList.size(); x++){
			if(!isOpen){
				currentDay = priceList.get(x).getClose();
				previousDay = priceList.get(x - 1).getClose();
				count = 0;
				if(currentDay.compareTo(previousDay) > 0){
					count++;
					for(int y = x - 2; y > 1; y--){
						previousDay = priceList.get(y).getClose();
						if(currentDay.compareTo(previousDay) == - 1){
							break;
						}else{
							count++;
						}if(count >= high){
							System.out.println(TradingSystem.getCurrencyPair().toString() + 
							" " + DateUtils.toUTCString(priceList.get(x).getDate()) + " " + currentDay
							+ " is at a " + count + " day high!");
							isOpen = true;
							break;
						}
					}
				}
			}else{
					int nextStart = closeLongFinder(priceList,priceList.get(x - 1).getClose(),x, high);
					x+= nextStart;
					isOpen = false;
				}
			}
		}
	
	public static void getWeightedAvg(List<PoloniexChartData> myList){
		for(int x = 0;x < myList.size();x++){
			System.out.println(myList.get(x).getVolume());
		}
	}
	
	public static int closeLongFinder(List<PoloniexChartData> priceList, BigDecimal open, int startPosition, int high){
		int z = startPosition;
		BigDecimal currentDay;
		BigDecimal previousDay;
		PARENT: for(int x = startPosition; x < priceList.size();x++){
			currentDay = priceList.get(x).getClose();
			previousDay = priceList.get(x-1).getClose();
			int count = 0;
			if(currentDay.compareTo(previousDay) < 0){
				count++;
				for(int y = x - 2; y > 1;y--){
					previousDay = priceList.get(y).getClose();
					if(currentDay.compareTo(previousDay) > 0){
						break;
					}else{
						count++;
					}if(count == high | cutLoss(open, currentDay)){
						BigDecimal close = currentDay;
						String utcDate = DateUtils.toUTCString(priceList.get(x).getDate());
						setProfitLoss(close, open, utcDate);
						z = x;
						break PARENT;
					}
				}
			}
		}
		return z - startPosition;
	}
	
	public static boolean cutLoss(BigDecimal open, BigDecimal current){
		BigDecimal closeOpenDif = current.subtract(open);
		BigDecimal closeIf = new BigDecimal(-0.3000);
		BigDecimal closeOpenDiv = closeOpenDif.divide(open,5);
		if(closeOpenDiv.compareTo(closeIf) < 0){
			return false;
		}else{
			return false;
		}
	}
	
	public static void setProfitLoss(BigDecimal close, BigDecimal open, String utcDate){
		BigDecimal closeOpenDif = close.subtract(open);
		BigDecimal closeOpenDivided = closeOpenDif.divide(open, 5);
		BigDecimal p = new BigDecimal(100);
		BigDecimal percent = closeOpenDivided.multiply(p);
		myList.add(percent);
		System.out.println(TradingSystem.getCurrencyPair().toString() + 
		" " + utcDate + " " + close 
		+ " closed! " + percent);
	}
	

	public static String getProfitLoss(){
		BigDecimal win = new BigDecimal(0);
		BigDecimal loss = new BigDecimal(0);
		int w = 0, l = 0;
		for(int x = 0; x < myList.size();x++){
			if(myList.get(x).intValue() >= 0){
				win = win.add(new BigDecimal(myList.get(x).intValue()));
				w++;
			}else{
				loss = loss.add(new BigDecimal(myList.get(x).intValue()));
				l++;
			}
		}
		BigDecimal winner = new BigDecimal(w);
		BigDecimal loser = new BigDecimal(l);
		BigDecimal profitWins, profitLosses;
		if(w != 0){
			profitWins = win.divide(winner, 5);
		}else{
			profitWins = new BigDecimal(0.00);
		}
		if(l != 0){
			profitLosses = loss.divide(loser, 5);

		}else{
			profitLosses = new BigDecimal(0.00);
		}
		return "Winners: " + w + " for profit of: " + profitWins + " Losers: " + l + " for profit of: " + profitLosses;
	}
	
}
