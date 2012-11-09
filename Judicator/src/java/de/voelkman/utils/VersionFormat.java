package de.voelkman.utils;

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;

public class VersionFormat extends NumberFormat{
	public static final String STRING_EXPERIMENTAL_STATE = "Prototype";
	public static final String VERSION_PREFIX = "v";
	public static final char VERSION_SEPERATOR = '.';
	private static final String CHARS = "0123456789";

	public static final String VERSION_SUBVERSION = "a";

	@Override
	public StringBuffer format(double x, StringBuffer buf, FieldPosition pos) {
		if(x < 0){
			buf.append(STRING_EXPERIMENTAL_STATE);
		}else{
			buf.append(VERSION_PREFIX);
			buf.append(x / 1000);
			buf.append(VERSION_SEPERATOR);
			buf.append((x % 1000) / 100);
			buf.append(VERSION_SEPERATOR);
			buf.append(x % 100);
			int y = (int)((x*1000)%1000);
			if(y > 0){
				buf.append(VERSION_SUBVERSION);
				buf.append(y);
			}
		}
		return buf;
	}

	@Override
	public StringBuffer format(long x, StringBuffer buf, FieldPosition pos) {
		return format((double)x,buf,pos);
	}

	@Override
	public Number parse(String arg0, ParsePosition arg1) {
		StringBuffer buf = new StringBuffer();
		for(char c : arg0.toCharArray()){
			if(CHARS.indexOf(c) > -1){
				buf.append(c);
			}else if(VERSION_SEPERATOR == c){
				buf.append(".");
			}
		}
		return new Double(buf.toString());
	}
	


}
