package ro.go.adrhc.util.optional;

import lombok.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class OptionalCollectionImpl<T>
		extends OptionalCollectionStatusImpl implements OptionalCollection<T> {
	private final Collection<T> collection;

	public OptionalCollectionImpl(boolean isMissing,
			boolean incomplete, Collection<T> collection) {
		super(isMissing, incomplete);
		this.collection = collection;
	}

	public static <T> OptionalCollection<T> of(Collection<T> collection) {
		return collection instanceof OptionalCollection ? (OptionalCollection) collection
				: new OptionalCollectionImpl<>(false, false, collection);
	}

	public static <T> OptionalCollection<T> ofIncomplete(Collection<T> collection) {
		return collection instanceof OptionalCollection ? (OptionalCollection) collection
				: new OptionalCollectionImpl<>(false, true, collection);
	}

	public static <T> OptionalCollection<T> ofMissing(Collection<T> collection) {
		return new OptionalCollectionImpl<>(true, true, collection);
	}

	@Override
	public Collection<T> get() {
		return collection;
	}

	@Override
	public boolean add(T t) {
		return collection.add(t);
	}

	@Override
	public boolean addAll(@NonNull Collection<? extends T> c) {
		return collection.addAll(c);
	}

	@Override
	public void clear() {
		collection.clear();
	}

	@Override
	public boolean contains(Object o) {
		return collection.contains(o);
	}

	@Override
	public boolean containsAll(@NonNull Collection<?> c) {
		return collection.containsAll(c);
	}

	@Override
	public boolean equals(Object o) {
		return collection.equals(o);
	}

	@Override
	public int hashCode() {
		return collection.hashCode();
	}

	@Override
	public boolean isEmpty() {
		return collection.isEmpty();
	}

	@NonNull
	@Override
	public Iterator<T> iterator() {
		return collection.iterator();
	}

	@Override
	public Stream<T> parallelStream() {
		return collection.parallelStream();
	}

	@Override
	public boolean remove(Object o) {
		return collection.remove(o);
	}

	@Override
	public boolean removeAll(@NonNull Collection<?> c) {
		return collection.removeAll(c);
	}

	@Override
	public boolean removeIf(Predicate<? super T> filter) {
		return collection.removeIf(filter);
	}

	@Override
	public boolean retainAll(@NonNull Collection<?> c) {
		return collection.retainAll(c);
	}

	@Override
	public int size() {
		return collection.size();
	}

	@Override
	public Spliterator<T> spliterator() {
		return collection.spliterator();
	}

	@Override
	public Stream<T> stream() {
		return collection.stream();
	}

	@Override
	public Object[] toArray() {
		return collection.toArray();
	}

	@Override
	public <T1> T1[] toArray(T1[] a) {
		return collection.toArray(a);
	}

	@Override
	public <T1> T1[] toArray(IntFunction<T1[]> generator) {
		return collection.toArray(generator);
	}

	@Override
	public void forEach(Consumer<? super T> action) {
		collection.forEach(action);
	}
}
