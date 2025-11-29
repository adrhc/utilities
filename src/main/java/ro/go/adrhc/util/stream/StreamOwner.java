package ro.go.adrhc.util.stream;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static ro.go.adrhc.util.concurrency.FutureUtils.safelyGetAll;

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

	default T parallelReduce(T identity, BinaryOperator<T> accumulator) {
		return parallel().reduce(identity, accumulator);
	}

	default T reduce(T identity, BinaryOperator<T> accumulator) {
		return stream().reduce(identity, accumulator);
	}

	default <M, A, R> R mapCollect(
		Function<? super T, ? extends M> mapper,
		Collector<? super M, A, R> collector
	) {
		return map(mapper).collect(collector);
	}

	default <R, A> R parallelCollect(Collector<? super T, A, R> collector) {
		return parallel().collect(collector);
	}

	default <R, A> R collect(Collector<? super T, A, R> collector) {
		return parallel().collect(collector);
	}

	default <R> List<R> flatMapToList(Function<? super T, ? extends Stream<? extends R>> mapper) {
		return flatMap(mapper).toList();
	}

	default <R> List<R> mapOptionalsToList(Function<? super T, Optional<R>> mapper) {
		return mapOptionals(mapper).toList();
	}

	default <R> List<R> mapToList(Function<? super T, R> mapper) {
		return map(mapper).toList();
	}

	default IntStream mapToInt(ToIntFunction<? super T> mapper) {
		return stream().mapToInt(mapper);
	}

	default LongStream mapToLong(ToLongFunction<? super T> mapper) {
		return stream().mapToLong(mapper);
	}

	default <R> Stream<R> mapMulti(BiConsumer<? super T, ? super Consumer<R>> mapper) {
		return stream().mapMulti(mapper);
	}

	default <R> Stream<R> filterMap(
		Predicate<? super T> predicate, Function<? super T, ? extends R> mapper) {
		return stream().filter(predicate).map(mapper);
	}

	default <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
		return stream().flatMap(mapper);
	}

	default <R> Stream<R> parallelMapOptionals(Function<? super T, Optional<R>> mapper) {
		return parallel().flatMap(mapper.andThen(Optional::stream));
	}

	default <R> Stream<R> mapOptionals(Function<? super T, Optional<R>> mapper) {
		return stream().flatMap(mapper.andThen(Optional::stream));
	}

	/**
	 * Execute the mapping in parallel using the provided Executor.
	 */
	default <R> Stream<R> parallelMap(Executor executor, Function<? super T, ? extends R> mapper) {
		return safelyGetAll(
			stream().map(t -> CompletableFuture.supplyAsync(() -> mapper.apply(t), executor))
		);
	}

	default <R> Stream<R> parallelMap(Function<? super T, ? extends R> mapper) {
		return parallel().map(mapper);
	}

	default <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
		return stream().map(mapper);
	}

	default Stream<T> parallelFilter(Predicate<? super T> predicate) {
		return parallel().filter(predicate);
	}

	default Stream<T> filter(Predicate<? super T> predicate) {
		return stream().filter(predicate);
	}

	default Stream<T> parallel() {
		return stream().parallel();
	}

	Stream<T> stream();
}
