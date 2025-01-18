package ro.go.adrhc.util.stream;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface StreamOwner<T> {
	default void forEach(Consumer<? super T> consumer) {
		stream().forEach(consumer);
	}

	default Optional<T> findAny(Predicate<? super T> predicate) {
		return filter(predicate).findAny();
	}

	default Optional<T> findFirst(Predicate<? super T> predicate) {
		return filter(predicate).findFirst();
	}

	default boolean allMatch(Predicate<? super T> predicate) {
		return stream().allMatch(predicate);
	}

	default boolean noneMatch(Predicate<? super T> predicate) {
		return stream().noneMatch(predicate);
	}

	default boolean anyMatch(Predicate<? super T> predicate) {
		return stream().anyMatch(predicate);
	}

	default <R> List<R> mapOptionalToList(Function<? super T, Optional<? extends R>> mapper) {
		return mapOptional(mapper).toList();
	}

	default <R> List<R> flatMapToList(Function<? super T, ? extends Stream<? extends R>> mapper) {
		return flatMap(mapper).toList();
	}

	default <R> List<R> mapToList(Function<? super T, R> mapper) {
		return map(mapper).toList();
	}

	default <R> Stream<R> mapOptional(Function<? super T, Optional<? extends R>> mapper) {
		return stream().flatMap(o -> mapper.apply(o).stream());
	}

	default <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
		return stream().flatMap(mapper);
	}

	default <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
		return stream().map(mapper);
	}

	default <R> Stream<R> mapMulti(BiConsumer<? super T, Consumer<? super R>> mapper) {
		return stream().mapMulti(mapper);
	}

	default Stream<T> filter(Predicate<? super T> predicate) {
		return stream().filter(predicate);
	}

	Stream<T> stream();
}
