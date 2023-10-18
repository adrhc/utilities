package ro.go.adrhc.util.text;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ro.go.adrhc.util.collection.StreamUtils.stream;

public class StringUtils {
	public static boolean hasText(String text) {
		return !isBlank(text);
	}

	public static boolean isBlank(String text) {
		return text == null || text.isBlank();
	}

	public static <T> String concat(Function<T, String> toString, Iterable<T> iterable) {
		return stream(iterable).map(toString).collect(Collectors.joining("\n"));
	}

	public static <T> String concat(Iterable<T> iterable) {
		return concat("\n", iterable);
	}

	public static <T> String concat(Stream<T> stream) {
		return concat("\n", stream::iterator);
	}

	public static <T> String concat(String separator, Stream<T> stream) {
		return concat(separator, stream::iterator);
	}

	public static <T> String concat(String separator, Iterable<T> iterable) {
		return stream(iterable)
				.map(Object::toString)
				.collect(Collectors.joining(separator));
	}
}
