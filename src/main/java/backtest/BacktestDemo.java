package backtest;

import java.io.IOException;

public class BacktestDemo {

	public static void main(String[] args) throws IOException {
		ForexList forex = new ForexList("JPYUSD.csv");
		for(int i = 0; i < ForexList.dateList.size();i++){
			System.out.println(ForexList.getDateList().get(i) + " " + ForexList.getCloseList().get(i));
		}
		
		System.out.println(Backtest.runBackTest(ForexList.closeList));

	}

}
