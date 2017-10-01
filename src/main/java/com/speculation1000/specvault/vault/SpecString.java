package com.speculation1000.specvault.vault;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class SpecString {
	
	public static String prettySatsPrice(BigDecimal price){
		price = price.movePointRight(8);
		String pattern = "###,###,###";
		String number = new DecimalFormat(pattern).format(price);
		return number;
	}
	
	public static String prettyUSDPrice(BigDecimal price){
		String pattern = "0.00";
		String number = new DecimalFormat(pattern).format(price);
		return number;
	}
}

