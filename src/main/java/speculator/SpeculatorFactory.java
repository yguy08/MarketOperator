package speculator;

import market.MarketsEnum;
import vault.Config;

public class SpeculatorFactory {
	
	//set speculator
	public static Speculator createSpeculator(int balance, int risk, int maxUnits, 
			int stopLength, int timeFrameDays, int entryFlag, int exitFlag, boolean longOnly, boolean sortVol){
		
		if(Config.getMarket().getMarketName().equalsIgnoreCase(MarketsEnum.BITCOIN.getMarketName())){
			return new DigitalSpeculator(balance, risk, maxUnits, stopLength, timeFrameDays, entryFlag, exitFlag, longOnly, sortVol);
		}else if(Config.getMarket().getMarketName().equalsIgnoreCase(MarketsEnum.BITCOIN_OFFLINE.getMarketName())){
			return new DigitalSpeculator(balance, risk, maxUnits, stopLength, timeFrameDays, entryFlag, exitFlag, longOnly, sortVol);
		}		
		return null;
	}
	
	

}
