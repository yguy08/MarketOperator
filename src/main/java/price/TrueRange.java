package price;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import vault.Config;

public class TrueRange {
	
	public static void setTrueRange(List<PriceData> priceDataList){
		int movingAvg = Config.getMovingAvg();		
		//set first TR for 0 position (H-L)
		BigDecimal tR = priceDataList.get(0).getHigh().subtract(priceDataList.get(0).getClose()).abs();
		priceDataList.get(0).setTrueRange(tR);		
		for(int x = 1; x < movingAvg; x++){
			List<BigDecimal> trList = Arrays.asList(
					priceDataList.get(x).getHigh().subtract(priceDataList.get(x).getLow().abs(), MathContext.DECIMAL32),
					priceDataList.get(x).getHigh().subtract(priceDataList.get(x-1).getClose().abs(), MathContext.DECIMAL32),
					priceDataList.get(x-1).getClose().subtract(priceDataList.get(x).getLow().abs(), MathContext.DECIMAL32));				
				tR = tR.add(Collections.max(trList));
		}		
		tR = tR.divide(new BigDecimal(movingAvg), MathContext.DECIMAL32);		
		//initial up to MA get the same
		for(int x=1;x<movingAvg;x++){
			priceDataList.get(x).setTrueRange(tR);
		}		
		//20 exponential moving average
		for(int x = movingAvg; x < priceDataList.size();x++){
			List<BigDecimal> trList = Arrays.asList(
					priceDataList.get(x).getHigh().subtract(priceDataList.get(x).getLow().abs(), MathContext.DECIMAL32),
					priceDataList.get(x).getHigh().subtract(priceDataList.get(x-1).getClose().abs(), MathContext.DECIMAL32),
					priceDataList.get(x-1).getClose().subtract(priceDataList.get(x).getLow().abs(), MathContext.DECIMAL32));					
					tR = tR.multiply(new BigDecimal(movingAvg - 1), MathContext.DECIMAL32)
					.add((Collections.max(trList)), MathContext.DECIMAL32).
					divide(new BigDecimal(movingAvg), MathContext.DECIMAL32);					
					priceDataList.get(x).setTrueRange(tR);
		}
	}

}
