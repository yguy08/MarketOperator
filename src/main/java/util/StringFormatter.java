package util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import price.PriceData;

public class StringFormatter {
	
	/**
	 * Convert big decimal to eight decimal places (satoshi)
	 * @param big decimal
	 * @return String to display with eight decimal places
	 */
	public static String bigDecimalToEightString(BigDecimal unformattedBd){
		DecimalFormat formatter = new DecimalFormat("#.########");
		String formattedBd = formatter.format(unformattedBd);
		return formattedBd;
	}
	
	public static String bigDecimalToShortString(BigDecimal unformattedBd){
		DecimalFormat formatter = new DecimalFormat("#.##");
		String formattedBd = formatter.format(unformattedBd);
		return formattedBd;
	}
	
	public static String prettyPointX(BigDecimal unformattedBd){
		String bigFormat = PriceData.prettyPrice(unformattedBd);
		int length = (bigFormat.startsWith("-")) ? bigFormat.replace(",", "").length() - 1 : bigFormat.replace(",", "").length();
		int i = bigFormat.indexOf(",");
		if(length > 9){
			return bigFormat.substring(0, i) + "B";
		}else if(length > 6){
			return bigFormat.substring(0, i) + "M";
		}else if(length > 3){
			return bigFormat.substring(0, i) + "K";
		}else{
			return bigFormat;
		}		
	}

}
