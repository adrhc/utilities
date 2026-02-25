package ro.go.adrhc.util.stream;

import lombok.experimental.UtilityClass;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Spliterators.spliteratorUnknownSize;

@UtilityClass
public class StreamUtils {
	public static <T> HashSet<T> collectToHashSet(Stream<T> tStream) {
		return tStream.collect(Collectors.toCollection(HashSet::new));
	}

	public static <T> Stream<T> stream(Iterable<T> iterable) {
		return stream(iterable.iterator());
	}

	public static <T> Stream<T> stream(Iterator<T> iterator) {
		return stream(false, iterator);
	}

	public static <T> Stream<T> stream(boolean includeNull, Iterable<T> iterable) {
		return stream(includeNull, iterable.iterator());
	}

	public static <T> Stream<T> stream(boolean includeNull, Iterator<T> iterator) {
		return StreamSupport
			.stream(spliteratorUnknownSize(iterator, Spliterator.ORDERED), false)
			.filter(includeNull ? t -> true : Objects::nonNull);
	}
}
