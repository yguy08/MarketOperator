import java.math.BigDecimal;
import java.util.List;

import org.knowm.xchange.poloniex.dto.marketdata.PoloniexChartData;

public class Asset {
	
	private String name;
	private List<PoloniexChartData> priceList;
	private BigDecimal price;
	
	public Asset(String name, List<PoloniexChartData> poloniexChartData){
		this.name 		= name;
		this.price		= poloniexChartData.get((poloniexChartData.size() - 1)).getClose();
		this.priceList 	= poloniexChartData;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<PoloniexChartData> getPriceList(){
		return priceList;
		
	}
	
	public void setPriceList(List<PoloniexChartData> poloniexChartData){
		this.priceList = poloniexChartData;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	
	
	

}
