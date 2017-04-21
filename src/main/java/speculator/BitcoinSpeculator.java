package speculator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import entry.Entry;
import market.Market;
import market.MarketFactory;
import position.Position;

public class BitcoinSpeculator {
	
	//account balance b/c every speculator has an account
	private BigDecimal accountBalance;
	
	//risk
	private BigDecimal risk;
	
	//entry signal - break out # days used to calculate long or short entry
	private int entrySignalDays;
	
	//sell signal - break out # days in opposite direction of entry
	private int sellSignalDays;
	
	//max units
	private int maxUnits;
	
	//stop length -> entry price - ATR * stopLength
	private BigDecimal stopLength;
	
	//min volume
	private BigDecimal minVolume;
	
	//total return amount because every speculator can calculate end - start balance
	private BigDecimal totalReturnAmount;
	
	//total return percent because every speculator has a total return %
	private BigDecimal totalReturnPercent;
	
	//timeframe -> days been speculating. 0 for live -> 365 for a year
	private int timeFrameDays;
	
	//positions -> every speculator has positions either open or closed (open, closed)
	private List<Position> positionList;
	
	//entries -> every speculator has entries either taken or not taken
	private List<Entry> entryList; 
	
	//market because every speculator trades a market
	private Market market = null;	
	
	public BitcoinSpeculator(){
		accountBalance = new BigDecimal(0.00);
		risk = new BigDecimal(0.00);
		entrySignalDays = 0;
		sellSignalDays = 0;
		maxUnits = 0;
		stopLength = new BigDecimal(0.00);
		minVolume = new BigDecimal(0.00);
		totalReturnAmount = new BigDecimal(0.00);
		totalReturnPercent = new BigDecimal(0.00);
		timeFrameDays = 0;
		positionList = new ArrayList<>();
		entryList = new ArrayList<>();
		market = null;
	}
	
	public static BitcoinSpeculator createAverageRiskSpeculator(){
		BitcoinSpeculator bitcoinSpeculator = new BitcoinSpeculator();
		bitcoinSpeculator.setAccountBalance(new BigDecimal(0.00));
		bitcoinSpeculator.setRisk(new BigDecimal(2.00));
		bitcoinSpeculator.setEntrySignalDays(25);
		bitcoinSpeculator.setSellSignalDays(10);
		bitcoinSpeculator.setMaxUnits(6);
		bitcoinSpeculator.setStopLength(new BigDecimal(2.00));
		bitcoinSpeculator.setMinVolume(new BigDecimal(20.00));
		bitcoinSpeculator.setTimeFrameDays(365);
		MarketFactory mFactory = new MarketFactory();
		Market m = mFactory.createMarket(Market.DIGITAL_MARKET);
		bitcoinSpeculator.setMarket(m);
		return bitcoinSpeculator;
	}
	
	public void setAccountBalance(BigDecimal amount){
		accountBalance = amount;
	}
	
	public BigDecimal getAccountBalance(){
		return accountBalance;
	}
	
	public void setRisk(BigDecimal percent){
		risk = percent;
	}
	
	public BigDecimal getRisk() {
		return risk;
	}
	
	public void setEntrySignalDays(int numDays){
		entrySignalDays = numDays;
	}
	
	public int getEntrySignalDays(){
		return entrySignalDays;
	}
	
	public void setSellSignalDays(int numDays){
		sellSignalDays = numDays;
	}
	
	public int getSellSignalDays(){
		return sellSignalDays;
	}
	
	public void setMaxUnits(int numUnits){
		maxUnits = numUnits;
	}
	
	public int getMaxUnits() {
		return maxUnits;
	}
	
	public void setStopLength(BigDecimal multiplier){
		stopLength = multiplier;
	}
	
	public BigDecimal getStopLength() {
		return stopLength;
	}
	
	public void setMinVolume(BigDecimal volumeLimit){
		minVolume = volumeLimit;
	}
	
	public BigDecimal getMinVolume(){
		return minVolume;
	}
	
	public void setTotalReturnAmount(BigDecimal amount){
		totalReturnAmount = amount;
	}
	
	public BigDecimal getTotalReturnAmount(){
		return totalReturnAmount;
	}
	
	public void setTotalReturnPercent(BigDecimal percent){
		totalReturnPercent = percent;
	}
	
	public BigDecimal getTotalReturnPercent() {
		return totalReturnPercent;
	}
	
	public void setTimeFrameDays(int numDays){
		timeFrameDays = numDays;
	}
	
	public int getTimeFrameDays(){
		return timeFrameDays;
	}
	
	public List<Position> getPositionList(){
		return positionList;
	}
	
	public void setPositionList(List<Position> positionList){
		this.positionList = positionList;
	}
	
	public List<Entry> getEntryList(){
		return entryList;
	}
	
	public void setEntryList(List<Entry> entryList){
		this.entryList = entryList;
	}
	
	public void setMarket(Market market){
		this.market = market;
	}
	
	public Market getMarket(){
		return market;
	}
	
}
