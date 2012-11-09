/*
 * Created on 27.12.2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package de.voelkman.utils.gui;

import java.awt.Graphics;

import javax.swing.JLabel;

public class JVerticalLabel extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JVerticalLabel(String s) {
		super(s);
	}

	protected void paintComponent(Graphics g) {
		// ((Graphics2D)g).setTransform(AffineTransform.getRotateInstance(0.001,
		// -20, -20));
		super.paintComponent(g);
	}
}
