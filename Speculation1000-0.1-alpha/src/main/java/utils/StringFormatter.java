package utils;

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

}
