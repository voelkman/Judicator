package de.voelkman.utils.generator.helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class TabGenerator {

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */

	Map<String, Map<String, Integer>> pack = null;
	Set<String> title = null;
	Set<String> gender = null;

	public static void main(String[] args) throws FileNotFoundException {
		new TabGenerator().generate(new File("/workspace/BasicUtilities/helper/temp/Creatures.txt"), new File(
				"/workspace/BasicUtilities/src/de/voelkman/config/de/reference/Creatures.tab"), "Kreaturen");
		new TabGenerator().generate(new File("/workspace/BasicUtilities/helper/temp/Things.txt"), new File(
				"/workspace/BasicUtilities/src/de/voelkman/config/de/reference/Things.tab"), "Dinge");
	}

	public TabGenerator() {
		pack = new TreeMap<String, Map<String, Integer>>();
		title = new TreeSet<String>();
		gender = new TreeSet<String>();
		;
	}

	public void generate(File source, File target, String displayname) throws FileNotFoundException {
		readFile(source);
		write(target, displayname);
	}

	private void write(File target, String displayname) {
		if (target.exists()) {
			target.delete();
		}
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(target));
			// Header
			String gen = "";
			bw.write("#\"" + displayname + "\" publish\n");
			bw.write(";Start\n1,{ChooseFrom");
			String delim = "~";
			for (String nam : title) {
				for (String gen1 : gender) {
					if (pack.containsKey(nam + gen1)) {
						bw.write(delim + nam + gen1);
						delim = ",";
					}
				}
			}
			bw.write("}\n");
			bw.write("\n;Plural\n1,{ChooseFrom");
			delim = "~";
			gen = "Pl";
			for (String nam : title) {
				if (pack.containsKey(nam + gen)) {
					bw.write(delim + nam + gen);
					delim = ",";
				}

			}
			bw.write("}\n");
			bw.write("\n;Prefix\n1,{ChooseFrom");
			delim = "~";
			gen = "Pr";
			for (String nam : title) {
				if (pack.containsKey(nam + gen)) {
					bw.write(delim + nam + gen);
					delim = ",";
				}

			}
			bw.write("}\n");
			bw.write("\n;Genitiv\n1-3,des [GenitivM]\n1-2,der [GenitivF]\n1,des [GenitivN]\n");

			for (String gen1 : gender) {
				if (gen1.trim().length() > 0) {
					delim = "~";
					bw.write("\n;" + gen1 + "\n1,{ChooseFrom");
					for (String nam : title) {
						if (pack.containsKey(nam + gen1)) {
							bw.write(delim + nam + gen1);
							delim = ",";
						}
					}
					bw.write("}\n");
				}
			}
			for (String gen1 : gender) {if (gen1.trim().length() > 0) {
				delim = "~";
				bw.write("\n;Genitiv" + gen1 + "\n1,{ChooseFrom");
				for (String nam : title) {
					if (pack.containsKey(nam + "G" + gen1)) {
						bw.write(delim + nam + "G" + gen1);
						delim = ",";
					}
				}
				bw.write("}\n");}
			}

			// Lists
			for (Entry<String, Map<String, Integer>> e : pack.entrySet()) {
				bw.write("\n;" + e.getKey() + "\n");
				for (Entry<String, Integer> l : e.getValue().entrySet()) {
					bw.write("1");
					if (l.getValue().intValue() > 1)
						bw.write("-" + l.getValue().intValue());
					bw.write("," + l.getKey() + "\n");
				}
				bw.flush();
			}
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void readFile(File source) throws FileNotFoundException {

		BufferedReader fr = new BufferedReader(new FileReader(source));
		String line = null;
		try {
			while ((line = fr.readLine()) != null) {
				String[] parts = line.split(",");
				if (parts.length > 5) {
					String key[] = { parts[0] + parts[1], parts[0] + "Pl", parts[0] + "G" + parts[1], parts[0] + "Pr" };
					title.add(parts[0]);
					gender.add(parts[1]);
					for (int i = 0; i < key.length; i++) {
						if (parts[i + 2] != null && parts[i + 2].trim().length() > 0) {
							Map<String, Integer> m = pack.get(key[i]);
							if (m == null) {
								m = new TreeMap<String, Integer>();
								pack.put(key[i], m);
							}
							m.put(parts[i + 2], new Integer(parts[6]));
						}
					}
				}
			}
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("x");
	}

}
