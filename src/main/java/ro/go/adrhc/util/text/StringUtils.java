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

	public static <T> String concat(Stream<T> stream) {
		return concat("\n", Function.identity(), stream::iterator);
	}

	public static <T> String concat(String separator, Stream<T> stream) {
		return concat(separator, Function.identity(), stream::iterator);
	}

	public static <T> String concat(Iterable<T> iterable) {
		return concat("\n", Function.identity(), iterable);
	}

	public static <T, R> String concat(Function<T, R> mapper, Iterable<T> iterable) {
		return concat("\n", mapper, iterable);
	}

	public static <T> String concat(String separator, Iterable<T> iterable) {
		return concat(separator, Function.identity(), iterable);
	}

	public static <T, R> String concat(String separator, Function<T, R> mapper, Iterable<T> iterable) {
		return stream(iterable)
				.map(mapper)
				.map(Object::toString)
				.collect(Collectors.joining(separator));
	}
}
