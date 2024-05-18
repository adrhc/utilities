package ro.go.adrhc.util.stream;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface StreamAware<T> {
	default void forEach(Consumer<? super T> consumer) {
		rawStream().forEach(consumer);
	}

	default Optional<T> findFirst(Predicate<? super T> predicate) {
		return rawFilter(predicate).findFirst();
	}

	default boolean allMatch(Predicate<? super T> predicate) {
		return rawStream().allMatch(predicate);
	}

	default boolean noneMatch(Predicate<? super T> predicate) {
		return rawStream().noneMatch(predicate);
	}

	default boolean anyMatch(Predicate<? super T> predicate) {
		return rawStream().anyMatch(predicate);
	}

	default <R> Stream<R> rawMap(Function<? super T, ? extends R> mapper) {
		return rawStream().map(mapper);
	}

	default <R> Stream<R> rawFlatMap(Function<? super T, ? extends Optional<? extends R>> mapper) {
		return rawStream().flatMap(o -> mapper.apply(o).stream());
	}

	default Stream<T> rawFilter(Predicate<? super T> predicate) {
		return rawStream().filter(predicate);
	}

	Stream<T> rawStream();
}
