package de.voelkman.utils.generator.smith;

import java.util.Random;

public class Randomizer {
	

	private static final Random RANDOM = new Random();
	long val = -1;

	public Randomizer(long value) {
		val = value;
	}

	public int nextInt(int size) {
		int result = 0;
		if (val >= 0) {
			result = (int) (val % size);
			if (val > 0) {
				val = val / size;
			} else {
				val = -1;
			}
		} else {
			result = RANDOM.nextInt(size);
		}
		return result;
	}

	public void setValue(long value) {
		val = value;
	}

	public long getValue() {
		return val;
	}
}
