/*
 * Created on 14.11.2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package de.voelkman.utils.sdl;

public class ModifierCalculator {

	// private static final int RANGE[] = {2,6,15,30,60,80};

	public static int getRangeModifier(double distance) {
		double result = 6 - Math.sqrt(distance / 2) * 3;
		return (int) result;
	}

	public static int getRangeForModifier(int mod) {
		double x = ((6 - mod) / 3);
		double result = (x * x) * 2.2;
		return (int) result;
	}

	public static int getTargetSizeModifier(int distance) {
		int result = 0;
		return result;
	}

	public static int getDummyModifier(int distance) {
		int result = 0;
		return result;
	}
}
