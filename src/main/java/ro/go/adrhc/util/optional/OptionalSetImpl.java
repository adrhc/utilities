package ro.go.adrhc.util.optional;

import lombok.NonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class OptionalSetImpl<T> extends OptionalCollectionStatusImpl implements OptionalSet<T> {
	private final Set<T> set;

	public OptionalSetImpl(boolean isMissing, boolean incomplete, Set<T> set) {
		super(isMissing, incomplete);
		this.set = set;
	}

	public static <T> OptionalSet<T> of(Set<T> set) {
		return set instanceof OptionalSet ? (OptionalSet) set
			: new OptionalSetImpl<>(false, false, set);
	}

	public static <T> OptionalSet<T> ofIncomplete(Set<T> set) {
		return set instanceof OptionalSet ? (OptionalSet) set
			: new OptionalSetImpl<>(false, true, set);
	}

	public static <T> OptionalSet<T> ofMissing() {
		return new OptionalSetImpl<>(true, true, Set.of());
	}

	@Override
	public Set<T> get() {
		return set;
	}

	@Override
	public boolean add(T t) {
		return set.add(t);
	}

	@Override
	public boolean addAll(@NonNull Collection<? extends T> c) {
		return set.addAll(c);
	}

	@Override
	public void clear() {
		set.clear();
	}

	@Override
	public boolean contains(Object o) {
		return set.contains(o);
	}

	@Override
	public boolean containsAll(@NonNull Collection<?> c) {
		return set.containsAll(c);
	}

	@Override
	public boolean equals(Object o) {
		return set.equals(o);
	}

	@Override
	public int hashCode() {
		return set.hashCode();
	}

	@Override
	public boolean isEmpty() {
		return set.isEmpty();
	}

	@NonNull
	@Override
	public Iterator<T> iterator() {
		return set.iterator();
	}

	@Override
	public boolean remove(Object o) {
		return set.remove(o);
	}

	@Override
	public boolean removeAll(@NonNull Collection<?> c) {
		return set.removeAll(c);
	}

	@Override
	public boolean retainAll(@NonNull Collection<?> c) {
		return set.retainAll(c);
	}

	@Override
	public int size() {
		return set.size();
	}

	@Override
	public Spliterator<T> spliterator() {
		return set.spliterator();
	}

	@Override
	public Object[] toArray() {
		return set.toArray();
	}

	@Override
	public <T1> T1[] toArray(T1[] a) {
		return set.toArray(a);
	}

	@Override
	public Stream<T> parallelStream() {
		return set.parallelStream();
	}

	@Override
	public boolean removeIf(Predicate<? super T> filter) {
		return set.removeIf(filter);
	}

	@Override
	public Stream<T> stream() {
		return set.stream();
	}

	@Override
	public <T1> T1[] toArray(IntFunction<T1[]> generator) {
		return set.toArray(generator);
	}

	@Override
	public void forEach(Consumer<? super T> action) {
		set.forEach(action);
	}
}
