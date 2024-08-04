package ro.go.adrhc.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TextFormatUtils {
	public static String bold(String text) {
		return STR."\u001B[1m\{text}\u001B[0m";
	}

	public static String blue(String text) {
		return STR."\033[34m\{text}\033[0m";
	}

	public static String boldBlue(String text) {
		return bold(blue(text));
	}
}
