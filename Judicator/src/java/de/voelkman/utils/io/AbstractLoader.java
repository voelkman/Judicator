package de.voelkman.utils.io;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public abstract class AbstractLoader {
	private static final Logger log = LoggerFactory.getLogger(AbstractLoader.class);

	public static Document xmlStringToDocument(String pXml) {
		InputSource lInputSource = null;
		DocumentBuilderFactory lDocumentBuilderFactory = null;
		DocumentBuilder lDocumentBuilder = null;
		Document lDocument = null;

		// create a document builder
		try {
			lDocumentBuilderFactory = DocumentBuilderFactory.newInstance();

			/*
			 * try{ SchemaFactory factory =
			 * SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			 * Source schemaFile = new
			 * StreamSource(MapSaver.class.getClassLoader().
			 * getResourceAsStream("schema/mapfolder.xsd")); Schema schema =
			 * factory.newSchema(schemaFile);
			 * lDocumentBuilderFactory.setSchema(schema); }catch (SAXException
			 * e) { e.printStackTrace(); }
			 */
			/*
			 * lDocumentBuilderFactory.setXIncludeAware(true);
			 * lDocumentBuilderFactory.setValidating(true);
			 * lDocumentBuilderFactory.setNamespaceAware(true);
			 */
			lDocumentBuilder = lDocumentBuilderFactory.newDocumentBuilder();
			/*
			 * lDocumentBuilder.setEntityResolver(new EntityResolver(){
			 * 
			 * public InputSource resolveEntity(String arg0, String arg1) throws
			 * SAXException, IOException {
			 * log.debug(this.getClass().getName()+": "+ arg0+" - "+arg1);
			 * return null; }}); lDocumentBuilder.setErrorHandler(new
			 * ErrorHandler(){
			 * 
			 * public void error(SAXParseException arg0) throws SAXException {
			 * arg0.printStackTrace(); }
			 * 
			 * public void fatalError(SAXParseException arg0) throws
			 * SAXException { arg0.printStackTrace(); }
			 * 
			 * public void warning(SAXParseException arg0) throws SAXException {
			 * arg0.printStackTrace();
			 * 
			 * }});
			 */
		} catch (ParserConfigurationException pEx) {

			log.error("File in Lister not Found", pEx);
		}

		if (pXml == null || pXml.length() < 1) {
			return null;
		}

		// the xml parser will find dtd's in the sub of the specified directory
		// lInputSource = new InputSource(lSystemId + pXml);

		lInputSource = new InputSource(pXml);
		// lInputSource.setSystemId("file:///" + lSystemId + "/dtd/");

		try {
			// do the conversion
			lDocument = lDocumentBuilder.parse(lInputSource);
		} catch (IOException pEx) {
			log.error("xmlStringToDocument IO", pEx);
		} catch (SAXException pEx) {
			log.error("xmlStringToDocument Sax", pEx);
		}

		return lDocument;
	}

	public static Document xmlStringToDocument(InputStream pXml) {
		InputSource lInputSource = null;
		DocumentBuilderFactory lDocumentBuilderFactory = null;
		DocumentBuilder lDocumentBuilder = null;
		Document lDocument = null;

		// create a document builder
		try {
			lDocumentBuilderFactory = DocumentBuilderFactory.newInstance();

			/*
			 * try{ SchemaFactory factory =
			 * SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			 * Source schemaFile = new
			 * StreamSource(MapSaver.class.getClassLoader().
			 * getResourceAsStream("schema/mapfolder.xsd")); Schema schema =
			 * factory.newSchema(schemaFile);
			 * lDocumentBuilderFactory.setSchema(schema); }catch (SAXException
			 * e) { e.printStackTrace(); }
			 */
			/*
			 * lDocumentBuilderFactory.setXIncludeAware(true);
			 * lDocumentBuilderFactory.setValidating(true);
			 * lDocumentBuilderFactory.setNamespaceAware(true);
			 */
			lDocumentBuilder = lDocumentBuilderFactory.newDocumentBuilder();
			/*
			 * lDocumentBuilder.setEntityResolver(new EntityResolver(){
			 * 
			 * public InputSource resolveEntity(String arg0, String arg1) throws
			 * SAXException, IOException {
			 * System.out.println(this.getClass().getName()+": "+
			 * arg0+" - "+arg1); return null; }});
			 * lDocumentBuilder.setErrorHandler(new ErrorHandler(){
			 * 
			 * public void error(SAXParseException arg0) throws SAXException {
			 * arg0.printStackTrace(); }
			 * 
			 * public void fatalError(SAXParseException arg0) throws
			 * SAXException { arg0.printStackTrace(); }
			 * 
			 * public void warning(SAXParseException arg0) throws SAXException {
			 * arg0.printStackTrace();
			 * 
			 * }});
			 */
		} catch (ParserConfigurationException pEx) {

			log.error("File in Lister not Found", pEx);
		}

		if (pXml == null) {
			return null;
		}

		// the xml parser will find dtd's in the sub of the specified directory
		// lInputSource = new InputSource(lSystemId + pXml);

		lInputSource = new InputSource(pXml);
		// lInputSource.setSystemId("file:///" + lSystemId + "/dtd/");

		try {
			// do the conversion
			lDocument = lDocumentBuilder.parse(lInputSource);
		} catch (IOException pEx) {
			log.error("xmlStringToDocument IO", pEx);
		} catch (SAXException pEx) {
			log.error("xmlStringToDocument Sax", pEx);
		}

		return lDocument;
	}

	public static String getTextFromNode(Node textnode) {
		StringBuffer result = new StringBuffer("");
		Node lNode = textnode.getFirstChild();
		while (textnode.hasChildNodes() && (lNode != null)) {
			if (lNode.getNodeType() == 3) {
				result.append(lNode.getNodeValue());
			} else {
				result.append(" ");
			}
			lNode = lNode.getNextSibling();

		}
		return result.toString();
	}

	public static int getIntFromNode(Node textnode) {
		int result = 0;
		Node lNode = textnode.getFirstChild();
		while (textnode.hasChildNodes() && (lNode != null) && (result == 0)) {
			if (lNode.getNodeType() == 3) {
				result = Integer.parseInt(lNode.getNodeValue().trim());
			}

			lNode = lNode.getNextSibling();

		}
		return result;
	}

	public static Color getColorFromString(String s) {
		return new Color(Integer.parseInt(s.substring(2, 4), 16), Integer.parseInt(s.substring(4, 6), 16), Integer
				.parseInt(s.substring(6, 8), 16), Integer.parseInt(s.substring(0, 2), 16));
	}

	public static String getAttributeValue(Node pNode, String att, String defaultValue) {
		String s = getAttributeValue(pNode,att);
		if(s != null){
			return s;
		}else{
			return defaultValue;
		}

	}
	
	public static String getAttributeValue(Node pNode, String att) {
		if ((pNode != null) && (pNode.getAttributes() != null) && (pNode.getAttributes().getNamedItem(att) != null)) {
			return (pNode.getAttributes().getNamedItem(att).getNodeValue());
		}
		return null;

	}

	public static int getAttributeValueAsInt(Node pNode, String att) {
		if ((pNode != null) && (pNode.getAttributes() != null) && (pNode.getAttributes().getNamedItem(att) != null)) {
			return Integer.parseInt(pNode.getAttributes().getNamedItem(att).getNodeValue());
		}
		return 0;

	}

	public static long getAttributeValueAsLong(Node pNode, String att) {
		if ((pNode != null) && (pNode.getAttributes() != null) && (pNode.getAttributes().getNamedItem(att) != null)) {
			return Long.parseLong(pNode.getAttributes().getNamedItem(att).getNodeValue());
		}
		return 0;

	}

	public static double getAttributeValueAsDouble(Node pNode, String att) {
		if ((pNode != null) && (pNode.getAttributes() != null) && (pNode.getAttributes().getNamedItem(att) != null)) {
			return Double.parseDouble(pNode.getAttributes().getNamedItem(att).getNodeValue());
		}
		return 0.0;

	}

	public AbstractLoader() {
		super();
	}

}