package ro.go.adrhc.util;

import lombok.experimental.UtilityClass;
import ro.go.adrhc.util.text.FontStyle;

import java.nio.file.Path;

@UtilityClass
public class ConsoleUtils {
	public static String format(FontStyle weight, String text) {
		return switch (weight) {
			case BOLD -> bold(text);
			case ITALIC -> italic(text);
			case NORMAL -> text;
		};
	}

	public static String underline(String text) {
		return "\u001B[4m%s\u001B[0m".formatted(text);
	}

	public static String italic(String text) {
		return "\u001B[3m%s\u001B[0m".formatted(text);
	}

	public static String bold(String text) {
		return "\033[1m%s\033[0m".formatted(text);
	}

	public static String bold(Path path) {
		return "\033[1m%s\033[0m".formatted(path);
	}

	public static String bold(boolean value) {
		return "\033[1m%b\033[0m".formatted(value);
	}

	public static String bold(int value) {
		return "\033[1m%d\033[0m".formatted(value);
	}

	public static String bold(long value) {
		return "\033[1m%d\033[0m".formatted(value);
	}

	public static String red(String text) {
		return "\033[31m%s\033[0m".formatted(text);
	}

	public static String red(Path path) {
		return "\033[31m%s\033[0m".formatted(path);
	}

	public static String green(String text) {
		return "\033[32m%s\033[0m".formatted(text);
	}

	public static String yellow(String text) {
		return "\033[33m%s\033[0m".formatted(text);
	}

	public static String yellow(Path path) {
		return "\033[33m%s\033[0m".formatted(path);
	}

	public static String blue(String text) {
		return "\033[34m%s\033[0m".formatted(text);
	}

	public static String boldRed(String text) {
		return "\033[1m\033[31m%s\033[0m".formatted(text);
	}

	public static String boldRed(Path path) {
		return "\033[1m\033[31m%s\033[0m".formatted(path);
	}

	public static String boldGreen(String text) {
		return "\033[1m\033[32m%s\033[0m".formatted(text);
	}

	public static String underlineGreen(String text) {
		return "\033[32m\u001B[4m%s\u001B[0m\033[0m".formatted(text);
	}

	public static String underlineYellow(String text) {
		return "\033[33m\u001B[4m%s\u001B[0m\033[0m".formatted(text);
	}

	public static String boldYellow(String text) {
		return "\033[1m\033[33m%s\033[0m".formatted(text);
	}

	public static String boldYellow(Path path) {
		return "\033[1m\033[33m%s\033[0m".formatted(path);
	}

	public static String boldBlue(String text) {
		return "\033[1m\033[34m%s\033[0m".formatted(text);
	}

	public static String boldBlue(Path path) {
		return "\033[1m\033[34m%s\033[0m".formatted(path);
	}
}
