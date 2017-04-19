package utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import asset.PoloniexOfflineChartData;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class PoloniexOfflineAdapter {
	
	
	public static List<PoloniexOfflineChartData> getPoloOfflineChartData(String assetName) throws IOException{
		
		List<PoloniexOfflineChartData> poloChartDataList = new ArrayList<>();
		
		int date = 0;
		BigDecimal high = new BigDecimal(0.00);
		BigDecimal low = new BigDecimal(0.00);
		BigDecimal open = new BigDecimal(0.00);
		BigDecimal close = new BigDecimal(0.00);
		BigDecimal volume = new BigDecimal(0.00);
		BigDecimal quoteVolume = new BigDecimal(0.00);
		BigDecimal weightedAverage = new BigDecimal(0.00);
		
		//byte[] polo = FileParser.readPoloniexFile(assetName);
		
		JsonFactory factory = new JsonFactory();
		//JsonParser parser = factory.createParser(polo);
		
		/*while(!parser.isClosed()){
			JsonToken jsonToken = parser.nextToken();
			if(JsonToken.FIELD_NAME.equals(jsonToken)){
				String fieldName = parser.getCurrentName();
				jsonToken = parser.nextToken();
				if("date".equals(fieldName)){
					date = parser.getIntValue();
				}else if("high".equals(fieldName)){
					high = new BigDecimal(parser.getFloatValue());
				}else if("low".equals(fieldName)){
					low = new BigDecimal(parser.getFloatValue());
				}else if("open".equals(fieldName)){
					open = new BigDecimal(parser.getFloatValue());
				}else if("close".equals(fieldName)){
					close = new BigDecimal(parser.getFloatValue());
				}else if("volume".equals(fieldName)){
					volume = new BigDecimal(parser.getFloatValue());
				}else if("quoteVolume".equals(fieldName)){
					quoteVolume = new BigDecimal(parser.getFloatValue());
				}else if("weightedAverage".equals(fieldName)){
					weightedAverage = new BigDecimal(parser.getFloatValue());
				}
			}else if(JsonToken.END_OBJECT.equals(jsonToken)){
				PoloniexOfflineChartData poloChartData = new PoloniexOfflineChartData(DateUtils.UnixTimestampToDate(date), high, low, open, close, volume, quoteVolume, weightedAverage);
				poloChartDataList.add(poloChartData);
			}
		}*/   
		
		return poloChartDataList;
		
	}
	
}
