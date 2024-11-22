package ro.go.adrhc.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Assert {
	public static void isFalse(boolean value, String message) {
		if (value) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void isTrue(boolean value, String message) {
		if (!value) {
			throw new IllegalArgumentException(message);
		}
	}
}
