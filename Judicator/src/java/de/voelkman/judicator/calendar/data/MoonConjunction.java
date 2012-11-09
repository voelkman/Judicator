/*
 * Created on 13.02.2009
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package de.voelkman.judicator.calendar.data;

import java.util.ArrayList;

public class MoonConjunction extends ArrayList<Moon> {

	int absoluteDaysFromZero = Integer.MAX_VALUE;

	public int getAbsoluteDaysFromZero() {
		return absoluteDaysFromZero;
	}

	public void setAbsoluteDaysFromZero(int absoluteDaysFromZero) {
		this.absoluteDaysFromZero = absoluteDaysFromZero;
	}
}
