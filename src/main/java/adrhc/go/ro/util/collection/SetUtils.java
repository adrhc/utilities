package adrhc.go.ro.util.collection;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class SetUtils {
	public static <K, V> Set<V> valuesHashSet(Map<K, V> map) {
		return new HashSet<>(map.values());
	}

	/**
	 * @return a LinkedHashSet preserving the Iterable order
	 */
	public static <T, R> Set<R> mapToSet(Iterable<T> iterable,
	                                     Function<? super T, ? extends R> mapper) {
		return StreamSupport.stream(iterable.spliterator(), false)
				.map(mapper)
				.collect(Collectors.toSet());
	}

	public static String sortThenJoin(Set<?> items) {
		return items.stream()
				.map(Object::toString)
				.sorted()
				.collect(Collectors.joining(" "));
	}

	@SafeVarargs
	public static <T> Set<T> setOf(T... value) {
		return Arrays.stream(value).filter(Objects::nonNull).collect(Collectors.toSet());
	}

	@SafeVarargs
	public static <T> Set<T> union(Set<T>... sets) {
		return setOf(sets).stream().flatMap(Set::stream).collect(Collectors.toSet());
	}

	public static <T> Set<T> limit(int maxSize, Collection<T> set) {
		return set.stream().limit(maxSize).collect(Collectors.toCollection(TreeSet::new));
	}
}
