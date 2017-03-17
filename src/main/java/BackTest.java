import java.util.List;

import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;

public class BackTest {
	
	static List<PoloniexChartData> priceList;
	
	public BackTest(List<PoloniexChartData> list){
		priceList = list;
	}
	
	public List<PoloniexChartData> getPriceList(){
		return priceList;
	}
}
