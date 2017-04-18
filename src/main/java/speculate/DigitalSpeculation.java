package speculate;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import backtest.BackTest;
import backtest.BackTestFactory;
import entry.Entry;
import market.Market;
import position.Position;
import utils.DateUtils;
import vault.Vault;

public class DigitalSpeculation implements Speculate {
	
	BigDecimal accountEquity;
	BigDecimal totalReturnPercent = new BigDecimal(0.00);
	
	List<Entry> entryList = new ArrayList<>();
	List<Entry> sortedEntryList = new ArrayList<>();
	
	List<Position> positionList = new ArrayList<>();
	List<Position> sortedPositionList = new ArrayList<>();
	
	List<Entry> unitList = new ArrayList<>();
	
	public DigitalSpeculation(Market market) {
		this.accountEquity = Speculate.DIGITAL_EQUITY;
	}
	
	public DigitalSpeculation(){
		
	}
	
	@Override
	public Speculate copy(Speculate speculate) {
		Speculate digitalSpeculation = new DigitalSpeculation();
		return digitalSpeculation;
	}

	@Override
	public void setAccountEquity(BigDecimal tradeResult) {
		this.accountEquity = this.accountEquity.add(tradeResult);
	}

	@Override
	public BigDecimal getAccountEquity() {
		return this.accountEquity;
	}
	
	@Override
	public void setTotalReturnPercent() {
		this.totalReturnPercent = this.accountEquity.subtract(Speculate.DIGITAL_EQUITY, MathContext.DECIMAL32)
				.divide(Speculate.DIGITAL_EQUITY, MathContext.DECIMAL32)
				.setScale(2, RoundingMode.HALF_DOWN)
				.multiply(new BigDecimal(100.00), MathContext.DECIMAL32);		
	}

	@Override
	public BigDecimal getTotalReturnPercent() {
		return this.totalReturnPercent;
	}
	
	public void getAllOpenPositions(Vault vault, Speculate speculate) {
		for(int i = 0; i < vault.backtest.getPositionList().size();i++){
			boolean isPositionOpen = vault.backtest.getPositionList().get(i).isOpen(); 
			if(isPositionOpen){
				vault.resultsList.add(vault.backtest.getPositionList().get(i).toString());
			}
		}
	}
	
	@Override
	public void getNewEntries(Vault vault, Speculate speculate) {
		for(int i = 0; i < vault.backtest.getEntryList().size(); i++){
			int days = DateUtils.getNumberOfDaysSinceDate(vault.backtest.getEntryList().get(i).getDateTime());
			if(days <= 7){
				vault.resultsList.add(vault.backtest.getEntryList().get(i).toString());
			}
		}
	}
	
	@Override
	public void getPositionsToClose(Vault vault, Speculate speculate) {
		for(int i = vault.backtest.getSortedPositionList().size() - 1; i > 0; i--){
			int days = DateUtils.getNumberOfDaysSinceDate(vault.backtest.getSortedPositionList().get(i).getDateTime());
			if(days <= 7){
				if(vault.backtest.getSortedPositionList().get(i).isOpen() == false){
					vault.resultsList.add(vault.backtest.getSortedPositionList().get(i).toString());
				}
			}else{
				break;
			}
		}
	}
	
	@Override
	public void runBackTest(Vault vault, Speculate speculate) {
		BackTestFactory backTestFactory = new BackTestFactory();
		BackTest runbacktest = backTestFactory.protoBackTest(vault.market, vault.speculate);
		runbacktest.setSortedEntryList(vault.backtest.getEntryList());
		runbacktest.setSortedPositionList(vault.backtest.getPositionList());
		runbacktest.protoBackTest();
		for(String results : runbacktest.getResultsList()){
			vault.resultsList.add(results);
		}
	}

	@Override
	public int getEntryDays() {
		return ENTRY;
	}

	@Override
	public int getExitDays() {
		return EXIT;
	}

	@Override
	public int getMaxUnits() {
		return MAX_UNITS;
	}

	@Override
	public BigDecimal getRisk() {
		return RISK;
	}

	@Override
	public BigDecimal getStop() {
		return STOP;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(" [ACCOUNT] ");
		sb.append(" Balance: " + this.accountEquity.setScale(2, RoundingMode.HALF_DOWN));
		sb.append(" Total Return: " + this.getTotalReturnPercent() + "%");
		sb.append(" Units: " + this.unitList.size());
		return sb.toString();
	}
	
}
