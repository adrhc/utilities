package ro.go.adrhc.util.stream;

import java.util.Comparator;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public interface SortedStreamOwner<T> extends StreamOwner<T> {
	default <R> Stream<R> mapMultiSorted(BiConsumer<? super T, ? super Consumer<R>> mapper) {
		return sortedStream().mapMulti(mapper);
	}

	default <R> Stream<R> mapSorted(Function<? super T, ? extends R> mapper) {
		return sortedStream().map(mapper);
	}

	default Stream<T> sortedStream() {
		return stream().sorted(comparator());
	}

	Comparator<T> comparator();
}
