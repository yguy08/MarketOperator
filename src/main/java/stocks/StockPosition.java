package stocks;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import operator.*;

public class StockPosition extends StockEntry {
	
	BigDecimal trueRange;
	BigDecimal N;
	BigDecimal entrySize;
	//BigDecimal totalPrice;
	StockChartData close;

	public StockPosition(String name, List<StockChartData> priceList){
		super(name, priceList);
		this.trueRange = setAverageTrueRange(priceList);
	}
	
	/*
	 * Calculate daily true range
	 * True Range = Maximum(H-L, H-PDC, PDC-L)
	 */
	public static BigDecimal setAverageTrueRange(List<StockChartData> priceList){
		
		//since formula requires a previous day's N value, must start with an x-day average of the True Range for calculation
		BigDecimal averageTrueRange = setInitialAverageTrueRange(priceList);
		
			for(int x = Position.LENGTH; x < priceList.size();x++){
				//calculate ranges of true range calculation
				BigDecimal highMinusLow 				= setAbsolute(priceList.get(x).getHigh().subtract(priceList.get(x).getLow(), MathContext.DECIMAL32));
				BigDecimal highMinusPreviousDayClose	= setAbsolute(priceList.get(x).getHigh().subtract(priceList.get(x - 1).getClose(), MathContext.DECIMAL32));
				BigDecimal previousDayCloseMinusLow		= setAbsolute(priceList.get(x-1).getClose().subtract(priceList.get(x).getLow(), MathContext.DECIMAL32));
				
				//trueRange
				BigDecimal trueRange;
				
				//EMA day
				BigDecimal e = new BigDecimal(Position.LENGTH - 1);
				BigDecimal z = new BigDecimal(Position.LENGTH);
				
				//Max of ranges = true range
				trueRange = highMinusLow.max(highMinusPreviousDayClose);
				trueRange = trueRange.max(previousDayCloseMinusLow);
				
				averageTrueRange = averageTrueRange.multiply(e, MathContext.DECIMAL32).add(trueRange, MathContext.DECIMAL32).divide(z, MathContext.DECIMAL32);
				
			}
			return averageTrueRange;

		}
	
	private static BigDecimal setAbsolute(BigDecimal subtract) {
		// TODO Auto-generated method stub
		return null;
	}

	public static BigDecimal setInitialAverageTrueRange(List<StockChartData> priceList){
		
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

}
