package ro.go.adrhc.util.stream;

import lombok.experimental.UtilityClass;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class StreamUtils {
	public static <T> HashSet<T> collectToHashSet(Stream<T> tStream) {
		return tStream.collect(Collectors.toCollection(HashSet::new));
	}

	public static <T, R> R collect(Supplier<R> supplier,
			BiConsumer<R, ? super T> accumulator,
			BiConsumer<R, R> combiner, Collection<T> collection) {
		return collection.stream().collect(supplier, accumulator, combiner);
	}

	public static <T> Optional<T> findAny(Predicate<? super T> predicate, Collection<T> collection) {
		return collection.stream().filter(predicate).findAny();
	}

	public static <T> Stream<T> stream(Iterable<T> iterable) {
		return stream(false, iterable);
	}

	public static <T> Stream<T> stream(boolean includeNull, Iterable<T> iterable) {
		Iterator<T> iterator = iterable.iterator();
		return Stream.iterate(null, _ -> iterator.hasNext(), _ -> null)
				.map(_ -> iterator.next())
				.filter(includeNull ? _ -> true : Objects::nonNull);
	}
}
