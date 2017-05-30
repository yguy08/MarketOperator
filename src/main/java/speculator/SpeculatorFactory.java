package speculator;

import market.MarketsEnum;

public class SpeculatorFactory {
	
	//default settings
	public static Speculator createDefaultSpeculator() {
		return DigitalSpeculator.createDefaultSpeculator();
	}
	
	//set speculator
	public static Speculator createSpeculator(String marketName, int balance, int risk, int maxUnits, int stopLength, int timeFrameDays){
		if(marketName.equalsIgnoreCase(MarketsEnum.BITCOIN.getMarketName())){
			return DigitalSpeculator.createUpdatedSpeculator(balance, risk, maxUnits, stopLength, timeFrameDays);
		}
		return null;
	}
	
	

}
