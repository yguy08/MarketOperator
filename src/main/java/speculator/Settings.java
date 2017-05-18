package speculator;

import java.math.BigDecimal;
import java.util.Date;

public class Settings {
	
	private BigDecimal balance;
	
	private BigDecimal risk;
	
	private int maxUnits;
	
	private BigDecimal stopLength;
	
	private Date dateStart;
	
	private Date dateEnd;
	
	private int entrySignalDays;
	
	private int sellSignalDays;
	
	private BigDecimal minVolume;
	
	private boolean useVolumeFilter;
	
	private boolean useLastTradeResultFilter;
	
	private int timeFrameDays;
	
	public Settings(){
		
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getRisk() {
		return risk;
	}

	public void setRisk(BigDecimal risk) {
		this.risk = risk;
	}

	public int getMaxUnits() {
		return maxUnits;
	}

	public void setMaxUnits(int maxUnits) {
		this.maxUnits = maxUnits;
	}

	public BigDecimal getStopLength() {
		return stopLength;
	}

	public void setStopLength(BigDecimal stopLength) {
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

	public BigDecimal getMinVolume() {
		return minVolume;
	}

	public void setMinVolume(BigDecimal minVolume) {
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

}
