/*
 * Created on 29.07.2008
 */
package de.voelkman.utils.generator.smith;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.voelkman.utils.StringFunctions;
import de.voelkman.utils.data.Die;

/**
 * 
 * @author g8712
 * @version 04.11.2008
 */
public class Generator {
	int cc = 0;
	private static final Logger LOG = LoggerFactory.getLogger(Generator.class);

	private static String base = null;

	/**
	 * Enumeration of configuration packages to be searched
	 */
	public static enum DOMAINS {
		names, persons, reference, places, treasure, miscellaneous, examples
	}

	private static final Random RANDOM = new Random();
	private static final char FUNCTION_BRACET_OPEN = '{';
	private static final char FUNCTION_BRACET_CLOSE = '}';
	private static final char LIST_BRACET_OPEN = '[';
	private static final char LIST_BRACET_CLOSE = ']';
	// private static final char CALC_BRACET = '|';
	private static final char VARIABLE_BRACET = '%';
	private static final char PARAMETER_SEPARATOR = ',';
	private static final String OMITABLES = "#/";
	private static final String LISTSTART = ":;";
	private static final char LINE_APPENDER = '_';

	public static enum FUNCTIONCALLS {
		None, CapEachWord, Cap, CR, Plural, Dice, AorAn, LCase, UCase, ChooseFrom
	};

	public static enum PREFUNCTIONCALLS {
		Loop, If, Select, Pick
	};

	public static enum COMPARE {
		equal, less, greater, lessequal, greaterequal, notequal
	}

	private static final String packageName = "de/voelkman/config/de/";

	private String buffer = "";

	private HashMap<String, ArrayList<String>> configuration = new HashMap<String, ArrayList<String>>();

	// private Map<String, String> attributes = null;
	private IGeneratorLibrary library = null;
	private String listname = null;
	private boolean loadedCorrectly = false;
	private HashMap<String, String> attributesDefault = new HashMap<String, String>();


	// private HashMap<String, String> attributesDefaultResolved = new
	// HashMap<String, String>();

	protected Generator(String name, IGeneratorLibrary lib) {
		library = lib;
		listname = name;
		loadedCorrectly = load(listname);
	}

	public synchronized static void setWorkingBase(String base) {
		Generator.base = base;
	}

	private boolean exists(String domain, String name) {
		if (base != null) {
			return (new File(base + "/Generators/" + domain + "/" + name + ".tab")).exists();
		} else {
			return Generator.class.getClassLoader().getResource(packageName + domain + "/" + name + ".tab") != null;
		}
	}

	private InputStream getStream(String domain, String name) {
		if (base != null) {
			try {
				return (new FileInputStream(base + "/Generators/" + domain + "/" + name + ".tab"));
			} catch (FileNotFoundException e) {
				LOG.error("File " + base + "/Generators/" + domain + "/" + name + ".tab not found. Retry with Classpath");
			}
		}
		return Generator.class.getClassLoader().getResourceAsStream(packageName + domain + "/" + name + ".tab");

	}

	private boolean load(String name) {

		BufferedReader fr = null;
		try {
			String domain = null;
			for (DOMAINS d : DOMAINS.values()) {
				// Get right domain
				domain = d.toString();
				if (exists(domain, name)) {
					LOG.debug(packageName + domain + "/" + name + ".tab found!");
					break;
				} else {
					domain = null;
				}
			}
			if (domain == null) {
				LOG.error(name + ".tab could not be found!");
				return false;
			}
			/*
			 * DataInputStream d = new DataInputStream(new FileInputStream(new
			 * File(Generator.class.getClassLoader().getResource( packageName +
			 * domain + "/" + name + ".tab").toURI())));
			 */
			DataInputStream d = new DataInputStream(getStream(domain, name));

			fr = new BufferedReader(new InputStreamReader(d, "UTF-8"));

			String s = fr.readLine();
			if (s == null) {
				LOG.error(packageName + domain + "/" + name + ".tab is empty!");
				return false;
			}
			while (s != null) {
				if (s.length() > 0 && OMITABLES.indexOf(s.charAt(0)) > -1) {
					// Ignore Comments
				} else if (s.length() > 0 && LISTSTART.indexOf(s.charAt(0)) > -1) {
					String title = s.substring(1);
					ArrayList<String> values = new ArrayList<String>();

					s = fr.readLine();
					while (s != null && s.length() > 0 && (s.indexOf(PARAMETER_SEPARATOR) >= 0 || (s.charAt(0) == LINE_APPENDER))) {
						// This is a line to be added
						if (s.startsWith("_")) {
							buffer += s.substring(1);
						} else {
							// first clear buffer
							if (buffer.length() > 0) {
								addLineToArrayList(values, buffer);
							}
							// set buffer
							buffer = s;
						}
						s = fr.readLine();

					}
					// flush buffer
					if (buffer.length() > 0) {
						addLineToArrayList(values, buffer);
						buffer = "";
					}
					// add configuration
					configuration.put(title, values);
				} else if (s.length() > 0 && s.charAt(0) == VARIABLE_BRACET) {
					int x = s.indexOf(VARIABLE_BRACET, 1);
					if (x > 1) {
						attributesDefault.put(s.substring(1, x), s.substring(x + 1));
					}
				}
				// Get next line for hile
				s = fr.readLine();
			}
			fr.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}/*
		 * catch (URISyntaxException e) { e.printStackTrace(); }
		 */catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fr.close();
			} catch (IOException ex) {
				LOG.error("Buffered Reader could not be closed", ex);
			} catch (NullPointerException ex) {
				// So no problem closing it
			}

		}

		return false;
	}

	private void addLineToArrayList(ArrayList<String> values, String s) {

		String key = s.substring(0, s.indexOf(PARAMETER_SEPARATOR));
		String value = s.substring(s.indexOf(PARAMETER_SEPARATOR) + 1);
		int count = 1;
		if (key.contains("-")) {
			count = Math.max(1, Integer.parseInt(key.substring(key.indexOf('-') + 1)) - Integer.parseInt(key.substring(0, key.indexOf('-'))) + 1);
		}
		for (int i = 0; i < count; i++) {
			values.add(value);
		}
	}

	public String evaluate() {
		return evaluate("Start", new HashMap<String, String>());
	}

	public String evaluate(String startPoint) {

		return evaluate(startPoint, new HashMap<String, String>());
	}

	public String evaluate(Map<String, String> attributes) {

		return evaluate("Start", new HashMap<String, String>(attributes));
	}

	protected String evaluate(String startPoint, Map<String, String> attributes) {
		if (!loadedCorrectly) {
			LOG.warn("Access to inavalid table " + listname);
			return "";
		}
		cc = 0;
		return resolve(getListResult(startPoint, attributes), attributes);
	
	}

	protected ArrayList<String> getList(String startPoint) {
		if (startPoint.contains(".")) {
			return library.getGenerator(startPoint.substring(0, startPoint.indexOf('.'))).getList(startPoint.substring(startPoint.indexOf('.') + 1));
		} else {
			return configuration.get(startPoint);
		}
	}

	protected String getListResult(String startPoint, Map<String, String> attributes) {
		String result = "";
		if (startPoint.contains(".")) {
			result = library.getGenerator(startPoint.substring(0, startPoint.indexOf('.'))).evaluate(startPoint.substring(startPoint.indexOf('.') + 1), attributes);
		} else {
			ArrayList<String> list = configuration.get(startPoint);
			if (list != null && list.size() > 0) {
				result = list.get(RANDOM.nextInt(list.size()));
			} else {
				result = "XXX (" + startPoint + " not found in " + listname + ") XXX";
			}
		}
		return result;
	}

	private String resolve(String resolvable, Map<String, String> attributes) {
		
		String s = resolvable;
		LOG.trace(s);
		boolean working = true;
		while (working) {
			s = resolveStructureFunctions(s, attributes);
			int function = s.lastIndexOf(FUNCTION_BRACET_OPEN);
			int functionclose = s.indexOf(FUNCTION_BRACET_CLOSE, function);
			int variable = s.lastIndexOf(LIST_BRACET_OPEN);
			int variableclose = s.indexOf(LIST_BRACET_CLOSE, variable);
			int calculationclose = s.lastIndexOf('|');
			int calculation = s.lastIndexOf('|', calculationclose);
			if(functionclose < 0 && function >=0){
				LOG.error(s +" is invalid (function)");
				s = "XXX invalid function delimiter XXX";
			}
			if(variableclose < 0 && variable >=0){
				LOG.error(s +" is invalid (variable)");
				s = "XXX invalid variable delimiter XXX";
			}
			if(calculation < 0 && calculationclose >=0){
				LOG.error(s +" is invalid (calculation)");
				s = "XXX invalid calculation delimiter XXX";
			}
			if (function > variable && function > calculation) {
				if (function < functionclose) {
					s = s.substring(0, function) + call(s.substring(function + 1, functionclose)) + ((s.length() > (functionclose + 1)) ? s.substring(functionclose + 1) : "");
				}
			} else if (variable > function && variable > calculation) {
				// resolve Variable
				if (variable < variableclose) {
					s = s.substring(0, variable) + getListResult(s.substring(variable + 1, variableclose), attributes) + ((s.length() > (variableclose + 1)) ? s.substring(variableclose + 1) : "");
				}
			} else if (calculation > function && calculation > variable) {
				// resolve Variable
				if (calculation < calculationclose) {
					s = s.substring(0, calculation) + calc(s.substring(calculation + 1, calculationclose)) + ((s.length() > (calculationclose + 1)) ? s.substring(calculationclose + 1) : "");
				}
			} else {
				// function == variable == -1
				working = false;
			}
		}
		cc--;
		return s;
	}

	private String resolveStructureFunctions(String structuredString, Map<String, String> attributes) {
		String s = structuredString;
		while (s.indexOf(VARIABLE_BRACET) > 0) {
			int v1 = s.indexOf(VARIABLE_BRACET);
			int v2 = s.indexOf(VARIABLE_BRACET, v1 + 1);
			if (v1 > 0 && v2 > 0 && v1 + 1 < v2) {
				String key = s.substring(v1 + 1, v2);
				String value = "";
				if (attributes != null && attributes.containsKey(key)) {
					value = attributes.get(s.substring(v1 + 1, v2));
				} else if (attributesDefault != null && attributesDefault.containsKey(key)) {
					// Null in order to circumvent deadlocks
					value = resolve(attributesDefault.get(key), attributes);
					attributes.put(key, value);
				}

				if (value == null) {
					value = "";
				}
				s = s.substring(0, v1) + value + s.substring(v2 + 1);
			}
		}
		for (PREFUNCTIONCALLS pf : PREFUNCTIONCALLS.values()) {
			while (s.contains(FUNCTION_BRACET_OPEN + pf.toString())) {
				ArrayList<String> param = cutOutArgumentsFromStringTrunc(s.substring(s.lastIndexOf(FUNCTION_BRACET_OPEN + pf.toString())));

				s = s.substring(0, s.lastIndexOf(FUNCTION_BRACET_OPEN + pf.toString())) + callStructureFunction(pf, param, attributes) + param.get(param.size() - 1);
			}

		}
		return s;
	}

	/**
	 * returns Seperated params Last entry is rest of String
	 * 
	 * @param function
	 * @return
	 */
	private ArrayList<String> cutOutArgumentsFromStringTrunc(String function) {
		ArrayList<String> result = new ArrayList<String>();
		String bb = "x";
		int lastParameterCut = function.indexOf('~') + 1;
		boolean running = true;
		for (int i = lastParameterCut; running && i <= function.length(); i++) {
			char c = function.charAt(i);
			switch (c) {
				case FUNCTION_BRACET_OPEN:
				case LIST_BRACET_OPEN: {
					bb = c + bb;
					break;
				}
				case FUNCTION_BRACET_CLOSE: {
					if (bb.charAt(0) == FUNCTION_BRACET_OPEN) {
						bb = bb.substring(1);
					} else if (bb.charAt(0) == 'x') {
						// We reached the end of the relevant part
						result.add(function.substring(lastParameterCut, i));
						lastParameterCut = i + 1;
						running = false;
					} else {
						LOG.error("FUNCTION_BRACET_CLOSE could not find correct format in "+function);
					}
					break;
				}
				case LIST_BRACET_CLOSE: {
					if (bb.charAt(0) == LIST_BRACET_OPEN) {
						bb = bb.substring(1);
					} /*
					 * else if (bb.charAt(0) == 'x') { // We reached the end of
					 * the relevant part
					 * result.add(function.substring(lastParameterCut, i));
					 * running = false; }
					 */else {
							LOG.error("LIST_BRACET_CLOSE could not find correct format in "+function);
					}
					break;
				}

				case PARAMETER_SEPARATOR: {
					if (bb.charAt(0) == 'x') {
						// This is a Seperator on the relevant level and so we
						// cunstruct a separate param
						result.add(function.substring(lastParameterCut, i));
						lastParameterCut = i + 1;
					}
					break;
				}
				default: {
				}
			}
		}
		if (lastParameterCut < function.length()) {
			result.add(function.substring(lastParameterCut));
		} else {
			result.add("");
		}
		return result;
	}

	private String callStructureFunction(PREFUNCTIONCALLS func, ArrayList<String> param, Map<String, String> attributes) {
		String result = "";
		switch (func) {
			case Select: {
				if (param.size() > 3) {
					String value = resolve(param.get(0), attributes);
					if ((param.size() & 1) == 0) {
						result = param.get(param.size() - 1);
					}
					for (int i = 1; i < param.size() - 1; i += 2) {
						if (param.get(i).equalsIgnoreCase(value)) {
							result = param.get(i + 1);
							break;
						}
					}
				} else {
					LOG.warn(listname + ": " + func + " has too few arguments, function is omitted");
					result = "*Select*";
				}
				break;
			}
			case Pick: {
				
				String error = "";
				ArrayList<String> list = new ArrayList<String>();

				int value = Integer.valueOf(resolve(param.get(0), attributes)).intValue()-1;
				
				for (int i = 1; i < param.size();i++) {
					ArrayList<String> s = getList(param.get(i));
					if (s != null) {
						list.addAll(s);
					} else {
						error += param.get(i) + " ";
					}
					if(list.size() > value){
						//We have what we need
						break;
					}
				}
				if (list != null  && list.size() > value) {
					result = list.get(value);
				} else {
					result = "XXX (Group " + param + " cannot resolve "+value+" in " + listname + ") XXX";
				}
				if (error.length() > 0) {
					result += "XXX " + error + " ommitted XXX";
					LOG.error(result);
				}

				break;
			}
			case If: {
				if (param.size() >= 2) {
					String s = resolve(param.get(0), attributes);
					int split = 0;
					String x = "";
					COMPARE op = COMPARE.equal;

					if (s.contains("<=")) {
						split = s.indexOf("<=");
						x = s.substring(0, split);
						s = s.substring(2 + split);
						op = COMPARE.lessequal;
					} else if (s.contains(">=")) {
						split = s.indexOf(">=");
						x = s.substring(0, split);
						s = s.substring(2 + split);
						op = COMPARE.greaterequal;
					} else if (s.contains("!=")) {
						split = s.indexOf("!=");
						x = s.substring(0, split);
						s = s.substring(2 + split);
						op = COMPARE.notequal;
					} else if (s.contains("=")) {
						split = s.indexOf("=");
						x = s.substring(0, split);
						s = s.substring(1 + split);
						op = COMPARE.equal;
					} else if (s.contains("<")) {
						split = s.indexOf("<");
						x = s.substring(0, split);
						s = s.substring(1 + split);
						op = COMPARE.less;
					} else if (s.contains(">")) {
						split = s.indexOf(">");
						x = s.substring(0, split);
						s = s.substring(1 + split);
						op = COMPARE.greater;
					}
					boolean cr = false;
					switch (op) {
						case equal: {
							cr = (s.equals(x));
							break;
						}
						case notequal: {
							cr = (!s.equals(x));
							break;
						}

						case less: {
							cr = (Integer.parseInt(x) < Integer.parseInt(s));
							break;
						}
						case lessequal: {
							cr = (Integer.parseInt(x) <= Integer.parseInt(s));
							break;
						}
						case greater: {
							cr = (Integer.parseInt(x) > Integer.parseInt(s));
							break;
						}
						case greaterequal: {
							cr = (Integer.parseInt(x) >= Integer.parseInt(s));
							break;
						}
					}
					if (cr) {
						result = param.get(1);
					}
				}
				break;
			}

			case Loop: {
				// int i = param.indexOf(',');
				if (param.size() >= 3) {
					// 2 Params and the Rest
					int loopcount = 1;
					try {
						loopcount = Integer.parseInt(resolve(param.get(0), attributes));
					} catch (NumberFormatException e) {
						loopcount = 1;
					}
					result = StringFunctions.loop(param.get(1), loopcount);
				}
				break;
			}
		}
		return result;
	}

	protected String call(String function) {
		FUNCTIONCALLS func = null;
		String param = "";
		if (function.contains("~")) {
			try {
				func = FUNCTIONCALLS.valueOf(function.substring(0, function.indexOf('~')));
			} catch (IllegalArgumentException e) {
				LOG.error(function + " in " + listname + " not supported!");
				func = FUNCTIONCALLS.None;
			}
			if (function.indexOf('~') + 1 < function.length()) {
				param = function.substring(function.indexOf('~') + 1);
			}
		} else {
			func = FUNCTIONCALLS.valueOf(function);
		}

		String result = "*" + func + "*";
		switch (func) {
			case Cap: {

				result = StringFunctions.capFirstWord(param);
				break;
			}
			case CapEachWord: {
				result = StringFunctions.capEachWord(param);
				break;
			}
			case CR: {
				result = System.getProperty("line.separator");
				break;
			}
			case Dice: {
				Die d = new Die(param);
				result = Integer.toString(d.roll());
				break;
			}
			case LCase: {
				result = param.toLowerCase();
				break;
			}
			case ChooseFrom: {
				String[] lists = param.split(",");
				String error = "";
				ArrayList<String> list = new ArrayList<String>();
				for (String startPoint : lists) {
					ArrayList<String> s = getList(startPoint);
					if (s != null) {
						list.addAll(s);
					} else {
						error += startPoint + " ";
					}
				}
				if (list != null && list.size() > 0) {
					result = list.get(RANDOM.nextInt(list.size()));
				} else {
					result = "XXX (Group " + param + " is complete empty in " + listname + ") XXX";
				}
				if (error.length() > 0) {
					result += "XXX " + error + " ommitted XXX";
					LOG.error(result);
				}

				break;
			}
		
			case Plural: {
				
				result = param + "s";
			}

			default: {
			}
		}
		return result;
	}

	private String calc(String substring) {
		// XXX Inset Calculations as soon as needed
		return "";
	}

	public Set<String> getParamKeys() {
		return attributesDefault.keySet();
	}

}
