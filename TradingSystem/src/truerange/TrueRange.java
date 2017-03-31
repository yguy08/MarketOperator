package truerange;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import trade.TradingSystem;

public class TrueRange {
		
	public TrueRange(){

	}
	
	/*
	 * Calculate daily true range
	 * True Range = Maximum(H-L, H-PDC, PDC-L)
	 */
	public static BigDecimal setAverageTrueRange(List<BigDecimal> priceList, int next){
		
		//since formula requires a previous day's N value, must start with an x-day average of the True Range for calculation
		BigDecimal averageTrueRange = setInitialAverageTrueRange(priceList, next);
		
			for(int x = next; x < priceList.size();x++){
				//calculate ranges of true range calculation
				BigDecimal highMinusLow 				= setAbsolute(priceList.get(x).subtract(priceList.get(x), MathContext.DECIMAL32));
				BigDecimal highMinusPreviousDayClose	= setAbsolute(priceList.get(x).subtract(priceList.get(x - 1), MathContext.DECIMAL32));
				BigDecimal previousDayCloseMinusLow		= setAbsolute(priceList.get(x-1).subtract(priceList.get(x), MathContext.DECIMAL32));
				
				//trueRange
				BigDecimal trueRange;
				
				//EMA day
				BigDecimal e = new BigDecimal(TradingSystem.HIGH_LOW - 1);
				BigDecimal z = new BigDecimal(TradingSystem.HIGH_LOW);
				
				//Max of ranges = true range
				trueRange = highMinusLow.max(highMinusPreviousDayClose);
				trueRange = trueRange.max(previousDayCloseMinusLow);
				
				averageTrueRange = averageTrueRange.multiply(e, MathContext.DECIMAL32).add(trueRange, MathContext.DECIMAL32).divide(z, MathContext.DECIMAL32);
				
			}
			return averageTrueRange;

		}
	
	public static BigDecimal setInitialAverageTrueRange(List<BigDecimal> priceList, int next){
		
		BigDecimal trueRange;
		BigDecimal highMinusLow;
		BigDecimal highMinusPreviousDayClose;
		BigDecimal previousDayCloseMinusLow;
		BigDecimal divisor = new BigDecimal(TradingSystem.HIGH_LOW);
		BigDecimal total = new BigDecimal(0);
		
		if(next > TradingSystem.HIGH_LOW * 2){
			for(int x = 0; x < next - TradingSystem.HIGH_LOW * 2 && x < priceList.size(); x++){
				if(x == 0){
					trueRange = setAbsolute(priceList.get(x).subtract(priceList.get(x), MathContext.DECIMAL32));
					total = total.add(trueRange, MathContext.DECIMAL32);
				}else{
					highMinusLow				= setAbsolute(priceList.get(x).subtract(priceList.get(x)));
					highMinusPreviousDayClose	= setAbsolute(priceList.get(x).subtract(priceList.get(x - 1)));
					previousDayCloseMinusLow	= setAbsolute(priceList.get(x - 1).subtract(priceList.get(x)));
					trueRange 					= highMinusLow.max(highMinusPreviousDayClose);
					trueRange 					= trueRange.max(previousDayCloseMinusLow);
					total 						= total.add(trueRange, MathContext.DECIMAL32);
					divisor						= new BigDecimal(x);
				}
			}
			
			trueRange = total.divide(divisor, MathContext.DECIMAL32);
			
		}else{
			for(int x = 0; x < TradingSystem.HIGH_LOW && x < priceList.size();x++){
				if(x == 0){
					trueRange = setAbsolute(priceList.get(x).subtract(priceList.get(x), MathContext.DECIMAL32));
					total = total.add(trueRange, MathContext.DECIMAL32);
				}else{
					highMinusLow				= setAbsolute(priceList.get(x).subtract(priceList.get(x)));
					highMinusPreviousDayClose	= setAbsolute(priceList.get(x).subtract(priceList.get(x - 1)));
					previousDayCloseMinusLow	= setAbsolute(priceList.get(x - 1).subtract(priceList.get(x)));
					trueRange 					= highMinusLow.max(highMinusPreviousDayClose);
					trueRange 					= trueRange.max(previousDayCloseMinusLow);
					total 						= total.add(trueRange, MathContext.DECIMAL32);
					divisor						= new BigDecimal(x);
				}
				
			}
			
			trueRange = total.divide(divisor, MathContext.DECIMAL32);
		}
		

		
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

}
