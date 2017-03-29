package position;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;

import trade.TradingSystem;

public class ProfitLoss {
	
	BigDecimal trueRange;
	BigDecimal N;
	BigDecimal entrySize;
	PoloniexChartData close;
	
	public static final int LENGTH = 20; 
	
	
	public static BigDecimal setEntrySize(BigDecimal trueRange,BigDecimal accountSize, BigDecimal risk){
		BigDecimal size = accountSize.multiply(risk, MathContext.DECIMAL32);
		size = size.divide(trueRange, MathContext.DECIMAL32);
		return size;
	}
	
	/*
	 * Calculate daily true range
	 * True Range = Maximum(H-L, H-PDC, PDC-L)
	 */
	public static BigDecimal setAverageTrueRange(List<PoloniexChartData> priceList){
		
		//since formula requires a previous day's N value, must start with an x-day average of the True Range for calculation
		BigDecimal averageTrueRange = setInitialAverageTrueRange(priceList);
		
			for(int x = LENGTH; x < priceList.size();x++){
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
				
			}
			return averageTrueRange;

		}
	
	public static BigDecimal setInitialAverageTrueRange(List<PoloniexChartData> priceList){
		
		BigDecimal trueRange;
		BigDecimal highMinusLow;
		BigDecimal highMinusPreviousDayClose;
		BigDecimal previousDayCloseMinusLow;
		BigDecimal divisor = new BigDecimal(TradingSystem.HIGH_LOW);
		BigDecimal total = new BigDecimal(0);
		
		for(int x = 0; x < TradingSystem.HIGH_LOW && x < priceList.size(); x++){
			if(x == 0){
				trueRange = setAbsolute(priceList.get(x).getHigh().subtract(priceList.get(x).getLow(), MathContext.DECIMAL32));
				total = total.add(trueRange, MathContext.DECIMAL32);
			}else{
				highMinusLow				= setAbsolute(priceList.get(x).getHigh().subtract(priceList.get(x).getLow()));
				highMinusPreviousDayClose	= setAbsolute(priceList.get(x).getHigh().subtract(priceList.get(x - 1).getClose()));
				previousDayCloseMinusLow	= setAbsolute(priceList.get(x - 1).getClose().subtract(priceList.get(x).getLow()));
				trueRange 					= highMinusLow.max(highMinusPreviousDayClose);
				trueRange 					= trueRange.max(previousDayCloseMinusLow);
				total 						= total.add(trueRange, MathContext.DECIMAL32);
				divisor						= new BigDecimal(x);
			}
		}
		
		trueRange = total.divide(divisor, MathContext.DECIMAL32);
		
		return trueRange;
		
	}
	
	public static PoloniexChartData closeLongFinder(List<PoloniexChartData> priceList, PoloniexChartData entry, BigDecimal trueRange){
		int z = priceList.indexOf(entry);
		PoloniexChartData c = entry;
		BigDecimal currentDay, previousDay;
		PARENT: for(int x = z; x < priceList.size();x++){
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
					}if(count == TradingSystem.CLOSE | cutLoss(entry.getClose(), currentDay, trueRange)){
						BigDecimal closed = currentDay;
						c = priceList.get(x);
						setProfitLoss(closed, entry.getClose(), priceList.get(x).getDate());
						z = x;
						break PARENT;
					}
				}
			}
		}
		return c;
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
	
	public static void setProfitLoss(BigDecimal close, BigDecimal open, Date date){
		BigDecimal closeOpenDif = close.subtract(open);
		BigDecimal closeOpenDivided = closeOpenDif.divide(open, 5);
		BigDecimal p = new BigDecimal(100);
		BigDecimal percent = closeOpenDivided.multiply(p);
		myList.add(percent);
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
	
	public static boolean cutLoss(BigDecimal open, BigDecimal current, BigDecimal trueRange){
		BigDecimal closeOpenDif = current.subtract(open);
		BigDecimal n = new BigDecimal(3);
		trueRange = trueRange.multiply(n, MathContext.DECIMAL32);
		if(closeOpenDif.compareTo(trueRange) > 0){
			return true;
		}else{
			return false;
		}
	}

}
