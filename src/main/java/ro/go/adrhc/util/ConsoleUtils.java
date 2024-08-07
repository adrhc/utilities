package ro.go.adrhc.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ConsoleUtils {
	public static String bold(String text) {
		return STR."\u001B[1m\{text}\u001B[0m";
	}

	public static String red(String text) {
		return STR."\033[31m\{text}\033[0m";
	}

	public static String green(String text) {
		return STR."\033[32m\{text}\033[0m";
	}

	public static String yellow(String text) {
		return STR."\033[33m\{text}\033[0m";
	}

	public static String blue(String text) {
		return STR."\033[34m\{text}\033[0m";
	}

	public static String boldRed(String text) {
		return bold(red(text));
	}

	public static String boldGreen(String text) {
		return bold(green(text));
	}

	public static String boldYellow(String text) {
		return bold(yellow(text));
	}

	public static String boldBlue(String text) {
		return bold(blue(text));
	}
}
