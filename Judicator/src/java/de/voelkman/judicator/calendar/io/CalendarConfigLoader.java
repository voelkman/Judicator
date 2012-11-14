/*
 * Created on 21.04.2005
 */
package de.voelkman.judicator.calendar.io;

import de.voelkman.judicator.calendar.data.Month;
import de.voelkman.utils.io.AbstractLoader;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * MapLoader.java
 *
 * @author g8712
 * @version 1.0.0
 *
 * History ----------- 21.04.2005 Creation
 */
public class CalendarConfigLoader extends AbstractLoader {

    // private static String FILE_SEPARATOR = HTGlobals.FILE_SEPARATOR;
    private static final Logger log = LoggerFactory.getLogger(CalendarConfigLoader.class);

    public static Object loadCalendarConfig(String listLocation) throws NullPointerException, ClassNotFoundException,
            SecurityException {
        log.info("Load: " + listLocation);
        InputStream in = CalendarConfigLoader.class.getClassLoader().getResourceAsStream("de/voelkman/judicator/calendar/xml/" + listLocation + ".xml");
        Document doc = xmlStringToDocument(in);

        Object result = null;
        // There should be only one ListerFolder Tag, so this is for merging
        // purposes only
        // Node node = lNodeList.item(0).getFirstChild();
        Node node = doc.getDocumentElement();
        if (node != null) {
            String s = getAttributeValue(node, "class");
            // OK we have an Object
            Node child = node.getFirstChild();
            while (child != null) {
                if (child.getNodeName().equalsIgnoreCase("months")) {
                    readMonths(child);
                }else
                if (child.getNodeName().equalsIgnoreCase("days")) {
                    readDays(child);
                }
                else
                if (child.getNodeName().equalsIgnoreCase("entries")) {
                   if(getAttributeValue(child, "name").equalsIgnoreCase("specialdays")){
//FIXME
                   }else{

                   }
                }
                child = child.getNextSibling();
            }



        }
        return result;
    }

    private static void readMonths(Node node) {
        Node child = node.getFirstChild();
        ArrayList<Month> result = new ArrayList<Month>();
        while (child != null) {
            if (child.getNodeName().equalsIgnoreCase("month")) {
                Month m = new Month();
                m.setName(getAttributeValue(child, "name"));
                m.setDaysInMonth(getAttributeValueAsInt(child, "name"));
                result.add(m);
            }
            child = child.getNextSibling();
        }
    }

    private static void readDays(Node node) {
        Node child = node.getFirstChild();
        ArrayList<String> result = new ArrayList<String>();
        while (child != null) {
            if (child.getNodeName().equalsIgnoreCase("day")) {
                result.add(getAttributeValue(child, "name"));
            }
            child = child.getNextSibling();
        }
    }
}