package ro.go.adrhc.util.stream;

import lombok.experimental.UtilityClass;

import java.util.HashSet;
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
		return stream(false, iterable);
	}

	public static <T> Stream<T> stream(boolean includeNull, Iterable<T> iterable) {
		return StreamSupport
			.stream(spliteratorUnknownSize(iterable.iterator(), Spliterator.ORDERED), false)
			.filter(includeNull ? t -> true : Objects::nonNull);
	}
}
