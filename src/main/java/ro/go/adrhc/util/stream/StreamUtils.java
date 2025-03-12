package ro.go.adrhc.util.stream;

import lombok.experimental.UtilityClass;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class StreamUtils {
	public static <T> HashSet<T> collectToHashSet(Stream<T> tStream) {
		return tStream.collect(Collectors.toCollection(HashSet::new));
	}

	public static <T> Stream<T> stream(Iterable<T> iterable) {
		return stream(false, iterable);
	}

	public static <T> Stream<T> stream(boolean includeNull, Iterable<T> iterable) {
		Iterator<T> iterator = iterable.iterator();
		return Stream.iterate(null, o -> iterator.hasNext(), o -> null)
				.map(o -> iterator.next())
				.filter(includeNull ? t -> true : Objects::nonNull);
	}
}
