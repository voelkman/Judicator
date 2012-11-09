package de.voelkman.utils;

import java.util.Map;
import java.util.Map.Entry;

public class StringFunctions {

	/**
	 * Displays the results of <code>value</code> the number of times specified
	 * by <code>repetition</code>.<br/>
	 * <b>Example:</b> loop(&quot;Hello!&quot;,3} will return
	 * &quot;Hello!Hello!Hello!&quot;.
	 * 
	 * @param value
	 * @param repetition
	 * @return constructed string
	 */
	public static final String loop(String value, int repetition) {
		return loop(value, null, repetition);
	}

	/**
	 * Displays the results of <code>value</code> the number of times specified
	 * by <code>repetition</code> with <code>separator</code> in between.<br/>
	 * <b>Example:</b> loop(&quot;Hello!&quot;,&quot;/&quot;,3} will return
	 * &quot;Hello!/Hello!/Hello!&quot;.
	 * 
	 * @param value
	 * @param separator
	 * @param repetition
	 * @return constructed string
	 */
	public static final String loop(String value, String separator, int repetition) {

		if (value == null) {
			return null;
		} else if (value.length() < 1) {
			return value;
		}
		if (repetition < 1) {
			// No Repetition
			return "";
		} else {
			StringBuffer s = new StringBuffer();
			boolean needsSeparator = false;
			for (int i = 0; i < repetition; i++) {
				if (needsSeparator) {
					s.append(separator);
				}
				s.append(value);
				if (i == 0) {
					needsSeparator = separator != null && (separator.length() > 0);
				}
			}
			return s.toString();
		}
	}

	/**
	 * This function will return <code>value</code> with each word capitalized.
	 * A "word" is determined by text with a space in front of it.
	 * 
	 * @param value
	 * @return
	 */
	public static String capEachWord(String value) {
		if (value == null) {
			return null;
		} else if (value.length() < 1) {
			return value;
		}
		char[] pchar = value.toCharArray();
		boolean capnext = true;
		for (int i = 0; i < value.length(); i++) {
			if (capnext) {
				pchar[i] = Character.toUpperCase(pchar[i]);
				capnext = false;
			} else if (pchar[i] == ' ') {
				capnext = true;
			}
		}
		return String.valueOf(pchar);
	}

	/**
	 * This function will return <code>value</code> with each word capitalized.
	 * A "word" is determined by text with a space in front of it.
	 * 
	 * @param value
	 * @return
	 */
	public static String capFirstWord(String value) {
		if (value == null) {
			return null;
		} else if (value.length() < 1) {
			return value;
		}
		char[] pchar = value.toCharArray();
		for (int i = 0; i < value.length(); i++) {
			if (pchar[i] != ' ') {
				pchar[i] = Character.toUpperCase(pchar[i]);
				break;
			}
		}
		return String.valueOf(pchar);
	}

	/**
	 * This constructs a single string with line breaks in order to be shown on
	 * console or message dialogs
	 * 
	 * @param strings
	 *            a Map with needed key-value Pairs to be shown
	 * @return
	 */
	public static String concatKeyValueForMessage(Map<String, String> strings) {
		StringBuffer buf = new StringBuffer();
		for (Entry<String, String> ee : strings.entrySet()) {
			buf.append(ee.getKey());
			buf.append(" - ");
			buf.append(ee.getValue());
			buf.append("\n");

		}
		return buf.toString();
	}

	/**
	 * Just a convenience method in order to remove typing errors from Input
	 * fieldsb
	 * 
	 * @return
	 */
	public static final int extractIntOutOfString(String source) {
		String buffer = "";
		char[] c = source.toCharArray();
		for (int i = 0; i < source.length(); i++) {
			if ((c[i] >= '0' && c[i] <= '9') || (c[i] == '-' && buffer.length() < 1)) {
				buffer += c[i];
			}
		}
		int res = 0;
		try {
			res = Integer.parseInt(buffer);
		} catch (NumberFormatException e) {
			res = 0;
		}
		return res;

	}
	
	public static final double extractDoubleOutOfString(String source) {
		String buffer = "";
		char[] c = source.toCharArray();
		for (int i = 0; i < source.length(); i++) {
			if ((c[i] >= '0' && c[i] <= '9') || (c[i] == '-' && buffer.length() < 1)|| (c[i] == '.')) {
				buffer += c[i];
			}
		}
		double res = 0;
		try {
			res = Double.parseDouble(buffer);
		} catch (NumberFormatException e) {
			res = 0;
		}
		return res;

	}

	public static String[] generateNumberArray(int start, int end) {
		String[] res = new String[end-start+1];
		for(int i = start; i <= end; i++){
			res[i-start] = Integer.toString(i);
		}
		return res;
	}
}
