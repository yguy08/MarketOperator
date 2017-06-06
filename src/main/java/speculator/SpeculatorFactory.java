package speculator;

import market.MarketsEnum;

public class SpeculatorFactory {
	
	//set speculator
	public static Speculator createSpeculator(String marketName, int balance, int risk, int maxUnits, 
			int stopLength, int timeFrameDays, int entryFlag, int exitFlag, boolean longOnly){
		
		if(marketName.equalsIgnoreCase(MarketsEnum.BITCOIN.getMarketName())){
			return new DigitalSpeculator(balance, risk, maxUnits, stopLength, timeFrameDays, entryFlag, exitFlag, longOnly);
		}
		
		return null;
	}
	
	

}
