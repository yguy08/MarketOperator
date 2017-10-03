package com.speculation1000.specvault.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SpecVaultStr {
	
	public static String bdToEightDecimal(BigDecimal bd){
		return bd.setScale(8, RoundingMode.HALF_EVEN).toString();
	}

}
