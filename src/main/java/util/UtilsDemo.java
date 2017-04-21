package util;

import java.math.BigDecimal;

public class UtilsDemo {

	public static void main(String[] args) {
		BigDecimal bd = new BigDecimal(0.00000004);;
		System.out.println(StringFormatter.bigDecimalToEightString(bd));

	}

}
