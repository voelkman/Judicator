/*
 * Created on 23.05.2008
 */
package de.voelkman.utils;

public final class MathArray {

	public static final long sum(long... values) {
		long result = 0;
		for (long i : values) {
			result += i;
		}
		return result;
	}

	public static final float sum(float... values) {
		float result = 0;
		for (float i : values) {
			result += i;
		}
		return result;
	}

	public static final double sum(double... values) {
		double result = 0;
		for (double i : values) {
			result += i;
		}
		return result;
	}

	public static final int sum(int... values) {
		int result = 0;
		for (int i : values) {
			result += i;
		}
		return result;
	}
}
