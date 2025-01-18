package ro.go.adrhc.util.stream;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public interface SortedStreamOwner<T> extends StreamOwner<T> {
	default <R> List<R> sortMapOptionalsToList(
			Function<? super T, Optional<? extends R>> mapper) {
		return sortMapOptionals(mapper).toList();
	}

	default <R> Stream<R> sortMapOptionals(Function<? super T, Optional<? extends R>> mapper) {
		return sortFlatMap(o -> mapper.apply(o).stream());
	}

	default <R> Stream<R> sortFlatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
		return sortedStream().flatMap(mapper);
	}

	default <R> Stream<R> sortMapMulti(BiConsumer<? super T, ? super Consumer<R>> mapper) {
		return sortedStream().mapMulti(mapper);
	}

	default <R> Stream<R> sortMap(Function<? super T, ? extends R> mapper) {
		return sortedStream().map(mapper);
	}

	default Stream<T> sortedStream() {
		return stream().sorted(comparator());
	}

	Comparator<T> comparator();
}
