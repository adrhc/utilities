package ro.go.adrhc.util.stream;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public interface SortedStreamOwner<T> extends StreamOwner<T> {
	default <R> List<R> sortedMapOptionalsToList(
			Function<? super T, Optional<? extends R>> mapper) {
		return sortedMapOptionals(mapper).toList();
	}

	default <R> Stream<R> sortedMapOptionals(Function<? super T, Optional<? extends R>> mapper) {
		return sortedFlatMap(o -> mapper.apply(o).stream());
	}

	default <R> Stream<R> sortedFlatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
		return sortedStream().flatMap(mapper);
	}

	default <R> Stream<R> sortedMapMulti(BiConsumer<? super T, ? super Consumer<R>> mapper) {
		return sortedStream().mapMulti(mapper);
	}

	default <R> Stream<R> sortedMap(Function<? super T, ? extends R> mapper) {
		return sortedStream().map(mapper);
	}

	default Stream<T> sortedStream() {
		return stream().sorted(comparator());
	}

	Comparator<T> comparator();
}
