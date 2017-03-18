import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;
import org.knowm.xchange.utils.DateUtils;

public class Position {
	
	String asset;
	PoloniexChartData entry;
	BigDecimal trueRange;
	BigDecimal N;
	static final int LENGTH = 20; 
	BigDecimal size = new BigDecimal(TradingSystem.ACCOUNT_SIZE);
	
	public Position(String asset, List<PoloniexChartData> priceList, PoloniexChartData entry){
		this.asset 		= asset;
		this.entry		= entry;
		this.trueRange	= setAverageTrueRange(priceList, entry);
	}
	
	/*
	 * Calculate daily true range
	 * True Range = Maximum(H-L, H-PDC, PDC-L)
	 */
	public static BigDecimal setAverageTrueRange(List<PoloniexChartData> priceList, PoloniexChartData entry){
		
		int start = priceList.indexOf(entry);
		
		//since formula requires a previous day's N value, must start with an x-day average of the True Range for calculation
		BigDecimal averageTrueRange = setInitialAverageTrueRange(priceList, entry, start);
		System.out.println("Initial Average True Range is: " + averageTrueRange);
		
		//start at start....
		for(int x = LENGTH; x < start;x++){
			//calculate ranges of true range calculation
			BigDecimal highMinusLow 				= setAbsolute(priceList.get(x).getHigh().subtract(priceList.get(x).getLow(), MathContext.DECIMAL32));
			BigDecimal highMinusPreviousDayClose	= setAbsolute(priceList.get(x).getHigh().subtract(priceList.get(x - 1).getClose(), MathContext.DECIMAL32));
			BigDecimal previousDayCloseMinusLow		= setAbsolute(priceList.get(x-1).getClose().subtract(priceList.get(x).getLow(), MathContext.DECIMAL32));
			
			//trueRange
			BigDecimal trueRange;
			
			//EMA day
			BigDecimal e = new BigDecimal(LENGTH - 1);
			BigDecimal z = new BigDecimal(LENGTH);
			
			//Max of ranges = true range
			trueRange = highMinusLow.max(highMinusPreviousDayClose);
			trueRange = trueRange.max(previousDayCloseMinusLow);
			
			averageTrueRange = averageTrueRange.multiply(e, MathContext.DECIMAL32).add(trueRange, MathContext.DECIMAL32).divide(z, MathContext.DECIMAL32);
			System.out.println("Date: " + priceList.get(x).getDate() + " The average true range is: " + averageTrueRange);
			
		}
		return averageTrueRange;
	}
	
	public static BigDecimal setInitialAverageTrueRange(List<PoloniexChartData> priceList, PoloniexChartData entryList, int start){
		
		List<PoloniexChartData> priceSubList;
		
		if(start < LENGTH){
			priceSubList		= priceList.subList(0, start);
		}else{
			priceSubList		= priceList.subList(0, LENGTH);
		}
				
		BigDecimal trueRange;
		BigDecimal highMinusLow;
		BigDecimal highMinusPreviousDayClose;
		BigDecimal previousDayCloseMinusLow;
		BigDecimal divisor = new BigDecimal(priceSubList.size());
		BigDecimal total = new BigDecimal(0);
		
		for(int x = 0; x < LENGTH; x++){
			if(x == 0){
				trueRange = setAbsolute(priceSubList.get(x).getHigh().subtract(priceSubList.get(x).getLow(), MathContext.DECIMAL32));
				total = total.add(trueRange, MathContext.DECIMAL32);
			}else{
				highMinusLow				= setAbsolute(priceSubList.get(x).getHigh().subtract(priceSubList.get(x).getLow()));
				highMinusPreviousDayClose	= setAbsolute(priceSubList.get(x).getHigh().subtract(priceSubList.get(x - 1).getClose()));
				previousDayCloseMinusLow	= setAbsolute(priceSubList.get(x - 1).getClose().subtract(priceSubList.get(x).getLow()));
				trueRange 					= highMinusLow.max(highMinusPreviousDayClose);
				trueRange 					= trueRange.max(previousDayCloseMinusLow);
				total 						= total.add(trueRange, MathContext.DECIMAL32);
			}
		}
		
		trueRange = total.divide(divisor, MathContext.DECIMAL32);
		
		return trueRange;
		
	}
	
	/*
	 * Helper method to get absolute of a big decimal number
	 * 
	 */
	public static BigDecimal setAbsolute(BigDecimal num){
		BigDecimal absolute = num.abs();
		return absolute;
		
	}
	
	/*
	 * Calculate N
	 * N = (19 x PDN + TR) / 20
	 * 
	 */
	public static BigDecimal setN(BigDecimal trueRange){
		return trueRange;
		
	}
	
	static List<BigDecimal> myList = new ArrayList<>();
	
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
	
	public static int closeLongFinder(List<PoloniexChartData> priceList, BigDecimal open, int startPosition, int high){
		int z = startPosition;
		BigDecimal currentDay, previousDay;
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
						Position.setProfitLoss(close, open, utcDate);
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

}
