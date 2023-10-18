package adrhc.go.ro.util.collection;

import java.util.Collection;
import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * sort(collection).by(keyExtractor)
 */
public record SortDsl<T>(Collection<T> collection) {
	public static <T> SortDsl<T> sort(Collection<T> collection) {
		return new SortDsl<>(collection);
	}

	public <U extends Comparable<? super U>> Stream<T> by(Function<? super T, U> keyExtractor) {
		return collection.stream().sorted(Comparator.comparing(keyExtractor));
	}
}
