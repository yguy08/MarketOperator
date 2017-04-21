package speculator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import entry.Entry;
import market.Market;
import market.MarketFactory;
import position.Position;

public class BitcoinSpeculator {
	
	//account balance b/c every speculator has an account
	private BigDecimal accountBalance = new BigDecimal(0.00);
	
	//start account balance
	private BigDecimal startAccountBalance = new BigDecimal(0.00);
	
	//risk
	private BigDecimal risk = new BigDecimal(0.00);
	
	//entry signal - break out # days used to calculate long or short entry
	private int entrySignalDays = 0;
	
	//sell signal - break out # days in opposite direction of entry
	private int sellSignalDays = 0;
	
	//max units
	private int maxUnits = 0;
	
	//stop length -> entry price - ATR * stopLength
	private BigDecimal stopLength = new BigDecimal(0.00);
	
	//min volume
	private BigDecimal minVolume = new BigDecimal(0.00);;
	
	//total return amount because every speculator can calculate end - start balance
	private BigDecimal totalReturnAmount = new BigDecimal(0.00);;
	
	//total return percent because every speculator has a total return %
	private BigDecimal totalReturnPercent = new BigDecimal(0.00);;
	
	//timeframe -> days been speculating. 0 for live -> 365 for a year
	private int timeFrameDays = 0;
	
	//positions -> every speculator has positions either open or closed (open, closed)
	private List<Position> positionList = new ArrayList<>();;
	
	//entries -> every speculator has entries either taken or not taken
	private List<Entry> entryList = new ArrayList<>();;
	
	//market because every speculator trades a market
	private Market market = null;	
	
	public BitcoinSpeculator(){
		
	}
	
	public static BitcoinSpeculator createAverageRiskSpeculator(){
		BitcoinSpeculator bitcoinSpeculator = new BitcoinSpeculator();
		bitcoinSpeculator.setAccountBalance(new BigDecimal(5.00));
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
		accountBalance = accountBalance.add(amount);
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
		totalReturnPercent = accountBalance.subtract(Speculator.DIGITAL_EQUITY, MathContext.DECIMAL32)
				.divide(Speculator.DIGITAL_EQUITY, MathContext.DECIMAL32)
				.setScale(2, RoundingMode.HALF_DOWN)
				.multiply(new BigDecimal(100.00), MathContext.DECIMAL32);
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
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(" [ACCOUNT] ");
		sb.append(" Balance: " + getAccountBalance().setScale(2, RoundingMode.HALF_DOWN));
		sb.append(" Total Return: " + getTotalReturnPercent() + "%");
		return sb.toString();
	}
	
}
