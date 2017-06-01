package util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

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
	
	public static String prettyPrice(BigDecimal price){		
		String uglyPrice = bigDecimalToEightString(price).replace(".", "").replaceFirst("^0+(?!$)", "");
		StringBuilder sb = new StringBuilder();
		for(int z = uglyPrice.length();z<8;z++){
			sb.append("0");
		}
		String prettyPrice = uglyPrice + sb.toString();
		return prettyPrice;
	}

}
