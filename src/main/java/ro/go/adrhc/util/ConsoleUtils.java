package ro.go.adrhc.util;

import lombok.experimental.UtilityClass;

import java.nio.file.Path;

@UtilityClass
public class ConsoleUtils {
	public static String bold(String text) {
		return STR."\033[1m\{text}\033[0m";
	}

	public static String bold(Path path) {
		return STR."\033[1m\{path}\033[0m";
	}

	public static String bold(boolean value) {
		return STR."\033[1m\{value}\033[0m";
	}

	public static String bold(int value) {
		return STR."\033[1m\{value}\033[0m";
	}

	public static String bold(long value) {
		return STR."\033[1m\{value}\033[0m";
	}

	public static String red(String text) {
		return STR."\033[31m\{text}\033[0m";
	}

	public static String red(Path path) {
		return STR."\033[31m\{path}\033[0m";
	}

	public static String green(String text) {
		return STR."\033[32m\{text}\033[0m";
	}

	public static String yellow(String text) {
		return STR."\033[33m\{text}\033[0m";
	}

	public static String yellow(Path path) {
		return STR."\033[33m\{path}\033[0m";
	}

	public static String blue(String text) {
		return STR."\033[34m\{text}\033[0m";
	}

	public static String boldRed(String text) {
		return STR."\033[1m\033[31m\{text}\033[0m";
	}

	public static String boldRed(Path path) {
		return STR."\033[1m\033[31m\{path}\033[0m";
	}

	public static String boldGreen(String text) {
		return STR."\033[1m\033[32m\{text}\033[0m";
	}

	public static String boldYellow(String text) {
		return STR."\033[1m\033[33m\{text}\033[0m";
	}

	public static String boldBlue(String text) {
		return STR."\033[1m\033[34m\{text}\033[0m";
	}
}
