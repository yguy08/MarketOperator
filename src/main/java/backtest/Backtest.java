package backtest;

import java.math.BigDecimal;
import java.util.List;

import operator.Entry;
import operator.Market;
import operator.TradingSystem;

public class Backtest {
	
	BigDecimal start;
	BigDecimal end;
	
	Market market;
	
	Entry entry;
	
	Result results;
	
	public Backtest(String market, String asset){
		this.market = new Market(market, asset);
		results = runBackTest();
	}
	
	//default no time specified
	public Result runBackTest(){
		if(market.getName().equals("digits")){
			if(market.getPoloAsset().getCloseList().size() < TradingSystem.HIGH_LOW){
				results.setEnoughHistory(false);
			}else{
				for(int x = TradingSystem.HIGH_LOW; x < market.getPoloAsset().getCloseList().size();x+=entry.next - x + 1){
					entry = new Entry(this.market, x);
					System.out.println(entry.next);
					System.out.println(entry.start);
					System.out.println(entry.entry);
					System.out.println(entry.trueRange);
					//find close
				}
			}
	
		}else{
			results.haveData(false);
		}
		
		return results;
		
	}
	
	public static BigDecimal calcATR(List<BigDecimal> closeList, int start){
		return new BigDecimal(0.00);
	}
	
	public static BigDecimal calcPositionSize(List<BigDecimal> closeList, int start, BigDecimal atr){
		return new BigDecimal(0.00);
	}
	
	public static boolean isAdd(List<BigDecimal> closeList, int start, BigDecimal atr, BigDecimal positionSize){
		return false;
	}
	
	public static boolean isClose(List<BigDecimal> closeList, int start, BigDecimal atr, BigDecimal positionSize){
		return false;
	}
	
	

}
