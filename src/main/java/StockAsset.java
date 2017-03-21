import java.math.BigDecimal;
import java.util.List;

public class StockAsset {
	
	private String name;
	private List<StockChartData> priceList;
	private BigDecimal price;
	
	public StockAsset(String name, List<StockChartData> stockPriceList){
		this.name 		= name;
		this.price		= stockPriceList.get(0).getClose();
		this.priceList 	= stockPriceList;
	}
	
	public List<StockChartData> getPriceList(){
		return priceList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
}
