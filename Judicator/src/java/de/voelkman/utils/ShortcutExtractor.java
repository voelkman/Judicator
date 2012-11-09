package de.voelkman.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ShortcutExtractor {
	private static org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(ShortcutExtractor.class);
	private TreeMap<String, String> shortcutmap = new TreeMap<String, String>();

	private String path = null;

	public ShortcutExtractor(String props) {
		path = props;
		load();
	}

	private static String extract(String source, int typo) {
		String[] parts = source.split(" ");
		String result = "";
		for (int i = 0; i < parts.length; i++) {
			boolean extend = ((typo & (1 << i)) > 0);
			result += parts[i].substring(0, extend ? 2 : 1);
		}
		int counter = typo / (1 << parts.length);
		if (counter > 0) {
			result += Integer.toString(counter);
		}
		return result;
	}

	public String extractShortCut(String source) {
		String res = shortcutmap.get(source.toLowerCase());
		if (res == null) {
			int i = 0;

			do {
				res = extract(source, i);
				i++;
			} while (res.length() < 2 || shortcutmap.values().contains(res));

			shortcutmap.put(source.toLowerCase(), res);
		}
		return res;
	}

	public void load() {
		if (path == null || path.length() < 1) {
			return;
		}
		File f = new File(path);
		if(!f.exists()){
			return;
		}
		InputSource lInputSource = null;
		DocumentBuilderFactory lDocumentBuilderFactory = null;
		DocumentBuilder lDocumentBuilder = null;
		Document lDocument = null;

		// create a document builder
		try {
			lDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
			lDocumentBuilder = lDocumentBuilderFactory.newDocumentBuilder();

		} catch (ParserConfigurationException pEx) {

			LOG.error("File in Lister not Found", pEx);
		}

		// the xml parser will find dtd's in the sub of the specified directory
		// lInputSource = new InputSource(lSystemId + pXml);

		lInputSource = new InputSource(path);
		// lInputSource.setSystemId("file:///" + lSystemId + "/dtd/");

		try {
			// do the conversion
			lDocument = lDocumentBuilder.parse(lInputSource);
		} catch (IOException pEx) {
			LOG.error("xmlStringToDocument IO", pEx);
		} catch (SAXException pEx) {
			LOG.error("xmlStringToDocument Sax", pEx);
		}
		NodeList nl = lDocument.getElementsByTagName("author");
		for(int i = 0; i < nl.getLength(); i++){
			Element el = (Element)nl.item(i);
			String sc = el.getAttribute("shortcut");
			String name = el.getTextContent();
			shortcutmap.put(name,sc);
		}

	}

	public void save() {
		File pFile = new File(path);
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		pFile.getParentFile().mkdirs();
		try {
			DOMSource source = new DOMSource(buildXMLString(shortcutmap));
			FileOutputStream os = new FileOutputStream(pFile);
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty("encoding", "ISO-8859-1");
			transformer.transform(source, new StreamResult(os));
			LOG.info("Saved to: " + pFile.getAbsolutePath());
		} catch (FileNotFoundException fnfex) {
			LOG.error("File in Lister not Found", fnfex);
		} catch (TransformerConfigurationException tcex) {
			LOG.error("TransformerConfigurationException", tcex);
		} catch (TransformerException tex) {
			LOG.error("TransformerException", tex);
		}
	}

	private static Document buildXMLString(Map<String, String> pack) {
		if (pack == null) {
			return null;
		}
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		Document doc = null;
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.newDocument();
			doc.setDocumentURI("http://www.voelkman.de/transformer");

			Element root = doc.createElement("pref");
			Element cont = doc.createElement("contributors");
			for (Entry<String, String> en : pack.entrySet()) {
				Element el = doc.createElement("author");
				el.appendChild(doc.createTextNode(en.getKey()));
				el.setAttribute("shortcut", en.getValue());
				cont.appendChild(el);
			}
			root.appendChild(cont);
			doc.appendChild(root);

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		return doc;
	}
}
