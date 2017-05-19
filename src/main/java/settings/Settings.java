package settings;

import java.util.Date;

public class Settings {
	
	private int balance;
	
	private int risk;
	
	private int maxUnits;
	
	private int stopLength;
	
	private Date dateStart;
	
	private Date dateEnd;
	
	private int entrySignalDays;
	
	private int sellSignalDays;
	
	private int minVolume;
	
	private boolean useVolumeFilter;
	
	private boolean useLastTradeResultFilter;
	
	private int timeFrameDays;
	
	//default settings
	public Settings(){
		setBalance(5);
		setRisk(1);
		setMaxUnits(7);
		setStopLength(2);
		setTimeFrameDays(365);
	}
	
	//set settings from settings view
	public Settings(int balance, int risk, int maxUnits, int stopLength, int timeFrameDays){
		setBalance(balance);
		setRisk(risk);
		setMaxUnits(maxUnits);
		setStopLength(stopLength);
		setTimeFrameDays(timeFrameDays);
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public int getRisk() {
		return risk;
	}

	public void setRisk(int risk) {
		this.risk = risk;
	}

	public int getMaxUnits() {
		return maxUnits;
	}

	public void setMaxUnits(int maxUnits) {
		this.maxUnits = maxUnits;
	}

	public int getStopLength() {
		return stopLength;
	}

	public void setStopLength(int stopLength) {
		this.stopLength = stopLength;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public int getEntrySignalDays() {
		return entrySignalDays;
	}

	public void setEntrySignalDays(int entrySignalDays) {
		this.entrySignalDays = entrySignalDays;
	}

	public int getSellSignalDays() {
		return sellSignalDays;
	}

	public void setSellSignalDays(int sellSignalDays) {
		this.sellSignalDays = sellSignalDays;
	}

	public int getMinVolume() {
		return minVolume;
	}

	public void setMinVolume(int minVolume) {
		this.minVolume = minVolume;
	}

	public boolean isUseVolumeFilter() {
		return useVolumeFilter;
	}

	public void setUseVolumeFilter(boolean useVolumeFilter) {
		this.useVolumeFilter = useVolumeFilter;
	}

	public boolean isUseLastTradeResultFilter() {
		return useLastTradeResultFilter;
	}

	public void setUseLastTradeResultFilter(boolean useLastTradeResultFilter) {
		this.useLastTradeResultFilter = useLastTradeResultFilter;
	}

	public int getTimeFrameDays() {
		return timeFrameDays;
	}

	public void setTimeFrameDays(int timeFrameDays) {
		this.timeFrameDays = timeFrameDays;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Balance: " + getBalance());
		sb.append(" Risk: " + getRisk());
		sb.append(" Units: " + getMaxUnits());
		sb.append(" Stop: " + getStopLength());
		sb.append(" Days: " + getTimeFrameDays());
		return sb.toString();
	}

}
