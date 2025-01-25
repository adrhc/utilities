package ro.go.adrhc.util.collection;

import lombok.experimental.UtilityClass;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@UtilityClass
public class SetUtils {
	/**
	 * @return from source
	 */
	public static <T, R> Set<T> common(Function<T, R> property, Set<T> source, Set<T> gauge) {
		return source.stream()
				.filter(s -> gauge.stream()
						.anyMatch(d -> Objects.equals(property.apply(s), property.apply(d))))
				.collect(Collectors.toSet());
	}

	/**
	 * @return from source (source minus toSubtract)
	 */
	public static <T, R> Set<T> diff(Function<T, R> property, Set<T> source, Set<T> toSubtract) {
		return source.stream()
				.filter(s -> toSubtract.stream()
						.noneMatch(d -> Objects.equals(property.apply(s), property.apply(d))))
				.collect(Collectors.toSet());
	}

	/**
	 * @return from source (source minus toSubtract)
	 */
	public static <T> Set<T> diff(BiPredicate<T, T> matchRule, Set<T> source, Set<T> toSubtract) {
		return source.stream()
				.filter(s -> toSubtract.stream().noneMatch(r -> matchRule.test(s, r)))
				.collect(Collectors.toSet());
	}

	public static <K, V> Set<V> valuesToHashSet(Map<K, V> map) {
		return new HashSet<>(map.values());
	}

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

	public static <T> Set<T> of(Stream<T> stream) {
		return stream.collect(Collectors.toSet());
	}

	@SafeVarargs
	public static <T> Set<T> ofNonNull(T... value) {
		return Arrays.stream(value).filter(Objects::nonNull).collect(Collectors.toSet());
	}

	@SafeVarargs
	public static <T> Set<T> union(Set<T>... sets) {
		return ofNonNull(sets).stream().flatMap(Set::stream).collect(Collectors.toSet());
	}

	public static <T> Set<T> limit(int maxSize, Collection<T> set) {
		return set.stream().limit(maxSize).collect(Collectors.toCollection(TreeSet::new));
	}
}
