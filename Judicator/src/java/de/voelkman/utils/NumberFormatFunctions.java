package de.voelkman.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NumberFormatFunctions {

	public static NumberFormat getPatternedDecimalFunction(String pattern){
		NumberFormat num = NumberFormat.getIntegerInstance();
		if(num instanceof DecimalFormat){
			((DecimalFormat)num).applyPattern(pattern);
		}
		return num;
		
	}

}
