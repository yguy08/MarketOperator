package speculator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import entry.Entry;
import position.Position;

public class DigitalSpeculatorOld implements Speculator {
	
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
	private List<Entry> entryList = new ArrayList<>();
	
	public DigitalSpeculatorOld(){
		
	}
	
	public static DigitalSpeculatorOld createAverageRiskSpeculator(){
		DigitalSpeculatorOld digitalSpeculator = new DigitalSpeculatorOld();
		digitalSpeculator.setAccountBalance(new BigDecimal(5.00));
		digitalSpeculator.setStartAccountBalance(digitalSpeculator.getAccountBalance());
		digitalSpeculator.setRisk(new BigDecimal(2.00));
		digitalSpeculator.setEntrySignalDays(25);
		digitalSpeculator.setSellSignalDays(10);
		digitalSpeculator.setMaxUnits(6);
		digitalSpeculator.setStopLength(new BigDecimal(2.00));
		digitalSpeculator.setMinVolume(new BigDecimal(20.00));
		digitalSpeculator.setTimeFrameDays(365);
		return digitalSpeculator;
	}
	
	public static DigitalSpeculatorOld createSpeculatorFromSettings(){
		DigitalSpeculatorOld digitalSpeculator = new DigitalSpeculatorOld();
		//digitalSpeculator.setAccountBalance(VaultSettingsController.getBalance());
		//digitalSpeculator.setStartAccountBalance(VaultSettingsController.getBalance());
		//digitalSpeculator.setRisk(VaultSettingsController.getRisk());
		digitalSpeculator.setEntrySignalDays(25);
		digitalSpeculator.setSellSignalDays(10);
		//digitalSpeculator.setMaxUnits(VaultSettingsController.getUnits());
		//digitalSpeculator.setStopLength(VaultSettingsController.getStop());
		digitalSpeculator.setMinVolume(new BigDecimal(20.00));
		//digitalSpeculator.setTimeFrameDays(VaultSettingsController.getDateRange());
		return digitalSpeculator;
	}

	@Override
	public void setAccountBalance(BigDecimal amount){
		accountBalance = accountBalance.add(amount);
	}
	
	@Override
	public BigDecimal getAccountBalance(){
		return accountBalance;
	}
	
	@Override
	public void setStartAccountBalance(BigDecimal amount) {
		startAccountBalance = amount;
		
	}

	@Override
	public BigDecimal getStartAccountBalance() {
		return startAccountBalance;
	}
	
	@Override
	public void setRisk(BigDecimal percent){
		risk = percent;
	}
	
	@Override
	public BigDecimal getRisk() {
		return risk;
	}
	
	@Override
	public void setEntrySignalDays(int numDays){
		entrySignalDays = numDays;
	}
	
	@Override
	public int getEntrySignalDays(){
		return entrySignalDays;
	}
	
	@Override
	public void setSellSignalDays(int numDays){
		sellSignalDays = numDays;
	}
	
	@Override
	public int getSellSignalDays(){
		return sellSignalDays;
	}
	
	@Override
	public void setMaxUnits(int numUnits){
		maxUnits = numUnits;
	}
	
	@Override
	public int getMaxUnits() {
		return maxUnits;
	}
	
	@Override
	public void setStopLength(BigDecimal multiplier){
		stopLength = multiplier;
	}
	
	@Override
	public BigDecimal getStopLength() {
		return stopLength;
	}
	
	@Override
	public void setMinVolume(BigDecimal volumeLimit){
		minVolume = volumeLimit;
	}
	
	@Override
	public BigDecimal getMinVolume(){
		return minVolume;
	}
	
	@Override
	public void setTotalReturnAmount(BigDecimal amount){
		totalReturnAmount = amount;
	}
	
	@Override
	public BigDecimal getTotalReturnAmount(){
		return totalReturnAmount;
	}
	
	@Override
	public void setTotalReturnPercent(){
		totalReturnPercent = accountBalance.subtract(startAccountBalance, MathContext.DECIMAL32)
				.divide(Speculator.DIGITAL_EQUITY, MathContext.DECIMAL32)
				.setScale(2, RoundingMode.HALF_DOWN)
				.multiply(new BigDecimal(100.00), MathContext.DECIMAL32);
	}
	
	@Override
	public BigDecimal getTotalReturnPercent() {
		return totalReturnPercent;
	}
	
	@Override
	public void setTimeFrameDays(int numDays){
		timeFrameDays = numDays;
	}
	
	@Override
	public int getTimeFrameDays(){
		return timeFrameDays;
	}
	
	@Override
	public List<Position> getPositionList(){
		return positionList;
	}
	
	@Override
	public void setPositionList(List<Position> positionList){
		this.positionList = positionList;
	}
	
	@Override
	public List<Entry> getEntryList(){
		return entryList;
	}
	
	@Override
	public void setEntryList(List<Entry> entryList){
		this.entryList = entryList;
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
