package adrhc.go.ro.util.collection;

import lombok.experimental.UtilityClass;

import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

@UtilityClass
public class StreamUtils {
	public static <T> Stream<T> stream(Iterable<T> iterable) {
		return stream(false, iterable);
	}

	public static <T> Stream<T> stream(boolean includeNull, Iterable<T> iterable) {
		Iterator<T> iterator = iterable.iterator();
		return Stream.generate(() -> null)
				.takeWhile(it -> iterator.hasNext())
				.map(n -> iterator.next())
				.filter(includeNull ? it -> true : Objects::nonNull);
	}
}
