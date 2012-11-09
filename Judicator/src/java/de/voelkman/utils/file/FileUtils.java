/*
 * Created on 29.10.2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package de.voelkman.utils.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

public class FileUtils {
	
	public static final String ABC = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz1234567890-\u00df\u00c4\u00d6\00dc\u00e4\u00F6\u00fc";
	

	public static void copy(File[] source, File targetfolder) {
		for (File sf : source) {
			if (sf.isFile()) {
				// System.out.print("copy " + sf.getName());
				File tf = new File(targetfolder.getAbsolutePath() + "/" + sf.getName());
				copy(sf, tf);
			}
		}
	}
	
	public static void move(File source, File target) {
		source.renameTo(target);
	}

	public static void copy(File source, File target) {
		target.getParentFile().mkdirs();
		BufferedOutputStream out = null;
		BufferedInputStream in = null;
		if (source.isFile()) {
			try {
				in = new BufferedInputStream(new FileInputStream(source));
				out = new BufferedOutputStream(new FileOutputStream(target));
				int read = 1;
				while ((read = in.read()) >= 0) {
					out.write(read);
				}
				out.flush();
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					// Yeah
				}
				try {
					out.close();
				} catch (IOException e) {
					// Yeah
				}
			}
		}
	}

	@Deprecated
	protected static void copyRessources(String sourcefolder, File targetfolder) throws URISyntaxException {
		File f = new File(FileUtils.class.getClassLoader().getResource(sourcefolder).toURI());
		if (f.exists() && f.isDirectory()) {
			for (String sf : f.list()) {
				System.out.println("copy " + sourcefolder + "/" + sf);
				File tf = new File(targetfolder.getAbsolutePath() + "/" + sf);
				copyRessource(sourcefolder + "/" + sf, tf);
			}
		}
	}

	@Deprecated
	private static void copyRessource(String source, File target) {
		target.getParentFile().mkdirs();
		BufferedOutputStream out = null;
		BufferedInputStream in = null;

		if (source != null) {
			try {
				// in = new BufferedInputStream(new FileInputStream(source));
				in = new BufferedInputStream(FileUtils.class.getClassLoader().getResourceAsStream(source));
				out = new BufferedOutputStream(new FileOutputStream(target));
				int read = 1;
				while ((read = in.read()) >= 0) {
					out.write(read);
				}
				out.flush();
			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					// Yeah
				}
				try {
					out.close();
				} catch (IOException e) {
					// Yeah
				}
			}
		}
	}
	
	public static String getFileName(String name) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < name.length(); i++) {
			if (ABC.indexOf(name.charAt(i)) >= 0) {
				result.append(name.charAt(i));
			}
		}
		return result.toString();
	}

}
