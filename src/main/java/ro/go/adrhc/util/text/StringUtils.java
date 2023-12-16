package ro.go.adrhc.util.text;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ro.go.adrhc.util.stream.StreamUtils.stream;

public class StringUtils {
    public static boolean hasText(String text) {
        return !isBlank(text);
    }

    public static boolean isBlank(String text) {
        return text == null || text.isBlank();
    }

    public static <T> String concat(Iterable<T> iterable) {
        return concat(stream(iterable));
    }

    public static <T, R> String concat(Function<T, R> mapper, Iterable<T> iterable) {
        return concat(mapper, stream(iterable));
    }

    public static <T> String concat(String separator, Iterable<T> iterable) {
        return concat(separator, stream(iterable));
    }

    public static <T, R> String concat(String separator, Function<T, R> mapper, Iterable<T> iterable) {
        return concat(separator, mapper, stream(iterable));
    }

    public static <T> String concat(Stream<T> stream) {
        return concat("\n", Function.identity(), stream);
    }

    public static <T, R> String concat(Function<T, R> mapper, Stream<T> stream) {
        return concat("\n", mapper, stream);
    }

    public static <T> String concat(String separator, Stream<T> stream) {
        return concat(separator, Function.identity(), stream);
    }

    public static <T, R> String concat(String separator, Function<T, R> mapper, Stream<T> stream) {
        return stream
                .map(mapper)
                .map(Object::toString)
                .collect(Collectors.joining(separator));
    }
}
