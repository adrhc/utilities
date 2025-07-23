package ro.go.adrhc.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * NOT SYNCHRONIZED!
 */
@AllArgsConstructor
@Getter
public class IntHolder {
	private int value;

	public int incrementAndGet() {
		return ++this.value;
	}

	public void subtract(int other) {
		this.value -= other;
	}

	public boolean isPositive() {
		return value > 0;
	}

	public boolean isNegative() {
		return value < 0;
	}

	public boolean isZero() {
		return value == 0;
	}
}
