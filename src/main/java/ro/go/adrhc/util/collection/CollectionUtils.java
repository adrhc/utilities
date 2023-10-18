package ro.go.adrhc.util.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionUtils {
	public static <T> T firstValue(Collection<T> values) {
		if (values == null) {
			return null;
		}
		return values.stream().filter(Objects::nonNull).findFirst().orElse(null);
	}

	public static <E, C extends Collection<E>> C add(C collection, E element) {
		collection.add(element);
		return collection;
	}

	public static <E, C extends Collection<E>> C addAll(C collection, Collection<E> elements) {
		collection.addAll(elements);
		return collection;
	}

	public static <E, P extends Collection<E>> List<P> partition(
			Supplier<P> partitionFactory, int size, Collection<E> tCollection) {
		return partition(partitionFactory, size, tCollection.stream());
	}

	/**
	 * @param partitionFactory creates the partition instance to which tStream items will be added
	 * @param size             is the partition's size
	 * @param tStream          contains the items to be partitioned
	 * @param <E>              tStream elements' type
	 * @param <P>              partition's type
	 * @return a list of partitions (i.e. tStream's subset)
	 */
	public static <E, P extends Collection<E>> List<P> partition(
			Supplier<P> partitionFactory, int size, Stream<E> tStream) {
		Partition<E> partition = new Partition<>(size, new ArrayList<>());
		List<P> result = tStream.peek(partition::add)
				.filter(it -> partition.isFull())
				.map(it -> partition.copyAndReset())
				.map(tmp -> createAndAddAll(partitionFactory, tmp.elements()))
				.collect(Collectors.toCollection(ArrayList::new));
		if (partition.isEmpty()) {
			return result;
		} else {
			P newPartition = createAndAddAll(partitionFactory, partition.elements());
			return add(result, newPartition);
		}
	}

	private static <E, C extends Collection<E>> C createAndAddAll(
			Supplier<C> collectionFactory, Collection<E> elements) {
		C collection = collectionFactory.get();
		return addAll(collection, elements);
	}

	private record Partition<T>(int capacity, List<T> elements) {
		public void add(T t) {
			elements.add(t);
		}

		public boolean isEmpty() {
			return elements.isEmpty();
		}

		public boolean isFull() {
			return elements.size() == capacity;
		}

		public Partition<T> copyAndReset() {
			Partition<T> partitionCopy = new Partition<>(capacity, List.copyOf(elements));
			elements.clear();
			return partitionCopy;
		}
	}
}
