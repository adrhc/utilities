package ro.go.adrhc.util.optional;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class OptionalStreamImpl<T> extends OptionalCollectionStatusImpl
		implements OptionalStream<T> {
	private final Stream<T> stream;

	public OptionalStreamImpl(boolean isMissing, boolean incomplete, Stream<T> stream) {
		super(isMissing, incomplete);
		this.stream = stream;
	}

	public static <T> OptionalStream<T> of(Stream<T> stream) {
		return stream instanceof OptionalStream ? (OptionalStream) stream
				: new OptionalStreamImpl<>(false, false, stream);
	}

	public static <T> OptionalStream<T> ofIncomplete(Stream<T> stream) {
		return stream instanceof OptionalStream ? (OptionalStream) stream
				: new OptionalStreamImpl<>(false, true, stream);
	}

	public static <T> OptionalStream<T> ofMissing() {
		return new OptionalStreamImpl<>(true, true, Stream.of());
	}

	@Override
	public Stream<T> get() {
		return stream;
	}

	@Override
	public boolean allMatch(Predicate<? super T> predicate) {
		return stream.allMatch(predicate);
	}

	@Override
	public boolean anyMatch(Predicate<? super T> predicate) {
		return stream.anyMatch(predicate);
	}

	@Override
	public <R, A> R collect(Collector<? super T, A, R> collector) {
		return stream.collect(collector);
	}

	@Override
	public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator,
			BiConsumer<R, R> combiner) {
		return stream.collect(supplier, accumulator, combiner);
	}

	@Override
	public long count() {
		return stream.count();
	}

	@Override
	public OptionalStream<T> distinct() {
		return of(stream.distinct());
	}

	@Override
	public OptionalStream<T> dropWhile(Predicate<? super T> predicate) {
		return of(stream.dropWhile(predicate));
	}

	@Override
	public OptionalStream<T> filter(Predicate<? super T> predicate) {
		return of(stream.filter(predicate));
	}

	@Override
	public Optional<T> findAny() {
		return stream.findAny();
	}

	@Override
	public Optional<T> findFirst() {
		return stream.findFirst();
	}

	@Override
	public <R> OptionalStream<R> flatMap(
			Function<? super T, ? extends Stream<? extends R>> mapper) {
		return of(stream.flatMap(mapper));
	}

	@Override
	public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
		return stream.flatMapToDouble(mapper);
	}

	@Override
	public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
		return stream.flatMapToInt(mapper);
	}

	@Override
	public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
		return stream.flatMapToLong(mapper);
	}

	@Override
	public void forEach(Consumer<? super T> action) {
		stream.forEach(action);
	}

	@Override
	public void forEachOrdered(Consumer<? super T> action) {
		stream.forEachOrdered(action);
	}

	@Override
	public OptionalStream<T> limit(long maxSize) {
		return of(stream.limit(maxSize));
	}

	@Override
	public <R> OptionalStream<R> map(Function<? super T, ? extends R> mapper) {
		return of(stream.map(mapper));
	}

	@Override
	public <R> OptionalStream<R> mapMulti(BiConsumer<? super T, ? super Consumer<R>> mapper) {
		return of(stream.mapMulti(mapper));
	}

	@Override
	public DoubleStream mapMultiToDouble(BiConsumer<? super T, ? super DoubleConsumer> mapper) {
		return stream.mapMultiToDouble(mapper);
	}

	@Override
	public IntStream mapMultiToInt(BiConsumer<? super T, ? super IntConsumer> mapper) {
		return stream.mapMultiToInt(mapper);
	}

	@Override
	public LongStream mapMultiToLong(BiConsumer<? super T, ? super LongConsumer> mapper) {
		return stream.mapMultiToLong(mapper);
	}

	@Override
	public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
		return stream.mapToDouble(mapper);
	}

	@Override
	public IntStream mapToInt(ToIntFunction<? super T> mapper) {
		return stream.mapToInt(mapper);
	}

	@Override
	public LongStream mapToLong(ToLongFunction<? super T> mapper) {
		return stream.mapToLong(mapper);
	}

	@Override
	public Optional<T> max(Comparator<? super T> comparator) {
		return stream.max(comparator);
	}

	@Override
	public Optional<T> min(Comparator<? super T> comparator) {
		return stream.min(comparator);
	}

	@Override
	public boolean noneMatch(Predicate<? super T> predicate) {
		return stream.noneMatch(predicate);
	}

	@Override
	public OptionalStream<T> peek(Consumer<? super T> action) {
		return of(stream.peek(action));
	}

	@Override
	public Optional<T> reduce(BinaryOperator<T> accumulator) {
		return stream.reduce(accumulator);
	}

	@Override
	public T reduce(T identity, BinaryOperator<T> accumulator) {
		return stream.reduce(identity, accumulator);
	}

	@Override
	public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator,
			BinaryOperator<U> combiner) {
		return stream.reduce(identity, accumulator, combiner);
	}

	@Override
	public OptionalStream<T> skip(long n) {
		return of(stream.skip(n));
	}

	@Override
	public OptionalStream<T> sorted() {
		return of(stream.sorted());
	}

	@Override
	public OptionalStream<T> sorted(Comparator<? super T> comparator) {
		return of(stream.sorted(comparator));
	}

	@Override
	public OptionalStream<T> takeWhile(Predicate<? super T> predicate) {
		return of(stream.takeWhile(predicate));
	}

	@Override
	public Object[] toArray() {
		return stream.toArray();
	}

	@Override
	public <A> A[] toArray(IntFunction<A[]> generator) {
		return stream.toArray(generator);
	}

	@Override
	public List<T> toList() {
		return stream.toList();
	}

	@Override
	public void close() {
		stream.close();
	}

	@Override
	public boolean isParallel() {
		return stream.isParallel();
	}

	@Override
	public Iterator<T> iterator() {
		return stream.iterator();
	}

	@Override
	public OptionalStream<T> onClose(Runnable closeHandler) {
		return of(stream.onClose(closeHandler));
	}

	@Override
	public OptionalStream<T> parallel() {
		return of(stream.parallel());
	}

	@Override
	public OptionalStream<T> sequential() {
		return of(stream.sequential());
	}

	@Override
	public Spliterator<T> spliterator() {
		return stream.spliterator();
	}

	@Override
	public OptionalStream<T> unordered() {
		return of(stream.unordered());
	}
}
