/*
 * Created on 05.12.2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package de.voelkman.utils.mock;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.VetoableChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputListener;

public class MegaListener implements InputMethodListener, ComponentListener, MouseInputListener, MouseListener,
		MouseMotionListener, KeyListener, PropertyChangeListener, VetoableChangeListener, AncestorListener, ActionListener,
		ChangeListener {
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(MegaListener.class);
	
	private void inform(String method, String args) {
		LOG.warn(method + ":  " + args);
	}

	public void caretPositionChanged(InputMethodEvent arg0) {
		inform("caretPositionChanged", arg0.paramString());
	}

	public void inputMethodTextChanged(InputMethodEvent arg0) {
		inform("inputMethodTextChanged", arg0.paramString());

	}

	public void componentHidden(ComponentEvent arg0) {
		inform("componentHidden", arg0.paramString());

	}

	public void componentMoved(ComponentEvent arg0) {
		inform("componentMoved", arg0.paramString());

	}

	public void componentResized(ComponentEvent arg0) {
		inform("componentResized", arg0.paramString());

	}

	public void componentShown(ComponentEvent arg0) {
		inform("componentShown", arg0.paramString());

	}

	public void mouseClicked(MouseEvent arg0) {
		inform("mouseClicked", arg0.paramString());

	}

	public void mouseEntered(MouseEvent arg0) {
		inform("mouseEntered", arg0.paramString());

	}

	public void mouseExited(MouseEvent arg0) {
		inform("mouseExited", arg0.paramString());

	}

	public void mousePressed(MouseEvent arg0) {
		inform("mousePressed", arg0.paramString());

	}

	public void mouseReleased(MouseEvent arg0) {
		inform("mouseReleased", arg0.paramString());

	}

	public void mouseDragged(MouseEvent arg0) {
		inform("mouseDragged", arg0.paramString());

	}

	public void mouseMoved(MouseEvent arg0) {
		inform("mouseMoved", arg0.paramString());

	}

	public void keyPressed(KeyEvent arg0) {
		inform("keyPressed", arg0.paramString());

	}

	public void keyReleased(KeyEvent arg0) {
		inform("keyReleased", arg0.paramString());

	}

	public void keyTyped(KeyEvent arg0) {
		inform("keyTyped", arg0.paramString());

	}

	public void propertyChange(PropertyChangeEvent arg0) {
		inform("propertyChange", arg0.getPropertyName() + " " + arg0.getOldValue() + " ->" + arg0.getNewValue());

	}

	public void vetoableChange(PropertyChangeEvent arg0) {
		inform("vetoableChange", arg0.getPropertyName() + " " + arg0.getOldValue() + " ->" + arg0.getNewValue());

	}

	public void ancestorAdded(AncestorEvent arg0) {
		inform("ancestorAdded", arg0.paramString());

	}

	public void ancestorMoved(AncestorEvent arg0) {
		inform("ancestorMoved", arg0.paramString());

	}

	public void ancestorRemoved(AncestorEvent arg0) {
		inform("ancestorRemoved", arg0.paramString());

	}

	public void actionPerformed(ActionEvent arg0) {
		inform("actionPerformed", arg0.getActionCommand());

	}

	public void stateChanged(ChangeEvent arg0) {
		inform("stateChanged", arg0.toString());

	}

	public static void infiltrate(Object o) {
		Method[] methods = o.getClass().getMethods();
		Object args[] = { new MegaListener() };
		for (Method m : methods) {
			if (m.getName().startsWith("add") && m.getName().endsWith("Listener")) {
				try {
					m.invoke(o, args);
				} catch (IllegalArgumentException e) {
					LOG.warn(e.getMessage());
				} catch (IllegalAccessException e) {
					LOG.warn(e.getMessage());
				} catch (InvocationTargetException e) {
					LOG.warn(e.getMessage());
				}
			}
		}
	}

}
