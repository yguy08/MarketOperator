package speculate;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import asset.Asset;
import asset.AssetFactory;
import backtest.BackTest;
import backtest.BackTestFactory;
import entry.Entry;
import market.Market;
import position.Position;
import utils.DateUtils;
import vault.Vault;

public class StockSpeculation implements Speculate {

	BigDecimal accountEquity;
	BigDecimal totalReturnPercent = new BigDecimal(0.00);
	
	List<Entry> entryList = new ArrayList<>();
	List<Entry> sortedEntryList = new ArrayList<>();
	
	List<Position> positionList = new ArrayList<>();
	List<Position> sortedPositionList = new ArrayList<>();
	
	List<Entry> unitList = new ArrayList<>();
	
	public StockSpeculation(Market market) {
		this.accountEquity = Speculate.STOCK_EQUITY;
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
		this.totalReturnPercent = this.accountEquity.subtract(Speculate.STOCK_EQUITY, MathContext.DECIMAL32)
				.divide(Speculate.STOCK_EQUITY, MathContext.DECIMAL32)
				.setScale(2, RoundingMode.HALF_DOWN)
				.multiply(new BigDecimal(100.00), MathContext.DECIMAL32); 
	}

	@Override
	public BigDecimal getTotalReturnPercent() {
		return this.totalReturnPercent;
	}
	
	public void getAllOpenPositions(Vault vault, Speculate speculate) {
		AssetFactory assetFactory = new AssetFactory();
		BackTestFactory backTestFactory = new BackTestFactory();
		Asset asset;
		BackTest backtest;
		for(int i=0; i < vault.market.getAssets().size();i++){
			asset = assetFactory.createAsset(vault.market, vault.market.getAssets().get(i).toString());
			backtest = backTestFactory.newBackTest(vault.market, asset, speculate);
			backtest.runBackTest();
			if(backtest.getLastPosition() != null && backtest.getLastPosition().isOpen()){
				vault.resultsList.add(backtest.getLastEntry().toString());
				vault.resultsList.add(backtest.getLastPosition().toString());
			}
		}
	}
	
	@Override
	public void getNewEntries(Vault vault, Speculate speculate) {
		BackTestFactory backTestFactory = new BackTestFactory(); 
		BackTest backtest = backTestFactory.protoBackTest(vault.market, speculate);
		backtest.protoBackTest();
		Date utcMidnight = DateUtils.getCurrentDateToUTCDateMidnight();
		for(int i = backtest.getSortedEntryList().size() - 1; i > 0; i--){
			if(backtest.getSortedEntryList().get(i).getDateTime().equals(utcMidnight)){
				vault.resultsList.add(backtest.getLastEntry().toString());
			}else{
				break;
			}
		}
	}

	@Override
	public void getPositionsToClose(Vault vault, Speculate speculate) {
		AssetFactory assetFactory = new AssetFactory();
		BackTestFactory backTestFactory = new BackTestFactory();
		Asset asset; 
		BackTest backtest;
		for(int i=0; i < vault.market.getAssets().size();i++){
			asset = assetFactory.createAsset(vault.market, vault.market.getAssets().get(i).toString());
			backtest = backTestFactory.newBackTest(vault.market, asset, speculate);
			backtest.runBackTest();
			if(backtest.getLastPosition() != null && backtest.getLastPosition().isOpen() == false){
				vault.resultsList.add(backtest.getLastEntry().toString());
				vault.resultsList.add(backtest.getLastPosition().toString());
			}
		}
	}
	
	@Override
	public void runBackTest(Vault vault, Speculate speculate) {
		BackTestFactory backTestFactory = new BackTestFactory(); 
		BackTest backtest = backTestFactory.protoBackTest(vault.market, speculate);
		backtest.protoBackTest();
		for(int i = 0; i < backtest.getResultsList().size();i++){
			vault.resultsList.add(backtest.getResultsList().get(i));
		}
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("[ACCOUNT] ");
		sb.append("Balance: $" + this.accountEquity.setScale(2, RoundingMode.HALF_DOWN));
		sb.append(" Total Return: " + this.getTotalReturnPercent() + "% ");
		sb.append(" Units: " + this.unitList.size());
		return sb.toString();
	}

	@Override
	public int getEntryDays() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getExitDays() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getMaxUnits() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BigDecimal getRisk() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getStop() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Speculate copy(Speculate speculate) {
		// TODO Auto-generated method stub
		return null;
	}

}