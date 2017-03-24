package operator;

import java.util.List;

import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;

public class Exit {

	static List<PoloniexChartData> priceList;
	List<PoloniexChartData> exitList;
	public Exit(List<PoloniexChartData> priceList){
		try {
			this.exitList = marketsToClose(priceList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
	public List<PoloniexChartData> marketsToClose(List<PoloniexChartData> priceList) throws Exception{
		return priceList;

	}
	
}
