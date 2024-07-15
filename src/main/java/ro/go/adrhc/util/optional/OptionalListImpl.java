package ro.go.adrhc.util.optional;

import lombok.NonNull;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class OptionalListImpl<T> extends OptionalStatusImpl implements OptionalList<T> {
	private final List<T> list;

	public OptionalListImpl(boolean isMissing, List<T> list) {
		super(isMissing);
		this.list = list;
	}

	public static <T> OptionalListImpl<T> of(List<T> list) {
		return new OptionalListImpl<>(false, list);
	}

	public static <T> OptionalListImpl<T> ofMissing() {
		return new OptionalListImpl<>(true, List.of());
	}

	@Override
	public List<T> get() {
		return list;
	}

	@Override
	public boolean add(T t) {
		return list.add(t);
	}

	@Override
	public void add(int index, T element) {
		list.add(index, element);
	}

	@Override
	public boolean addAll(@NonNull Collection<? extends T> c) {
		return list.addAll(c);
	}

	@Override
	public boolean addAll(int index, @NonNull Collection<? extends T> c) {
		return list.addAll(index, c);
	}

	@Override
	public void addFirst(T t) {
		list.addFirst(t);
	}

	@Override
	public void addLast(T t) {
		list.addLast(t);
	}

	@Override
	public void clear() {
		list.clear();
	}

	@Override
	public boolean contains(Object o) {
		return list.contains(o);
	}

	@Override
	public boolean containsAll(@NonNull Collection<?> c) {
		return list.containsAll(c);
	}

	@Override
	public boolean equals(Object o) {
		return list.equals(o);
	}

	@Override
	public T get(int index) {
		return list.get(index);
	}

	@Override
	public T getFirst() {
		return list.getFirst();
	}

	@Override
	public T getLast() {
		return list.getLast();
	}

	@Override
	public int hashCode() {
		return list.hashCode();
	}

	@Override
	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@NonNull
	@Override
	public Iterator<T> iterator() {
		return list.iterator();
	}

	@Override
	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}

	@NonNull
	@Override
	public ListIterator<T> listIterator() {
		return list.listIterator();
	}

	@NonNull
	@Override
	public ListIterator<T> listIterator(int index) {
		return list.listIterator(index);
	}

	@Override
	public T remove(int index) {
		return list.remove(index);
	}

	@Override
	public boolean remove(Object o) {
		return list.remove(o);
	}

	@Override
	public boolean removeAll(@NonNull Collection<?> c) {
		return list.removeAll(c);
	}

	@Override
	public T removeFirst() {
		return list.removeFirst();
	}

	@Override
	public T removeLast() {
		return list.removeLast();
	}

	@Override
	public void replaceAll(UnaryOperator<T> operator) {
		list.replaceAll(operator);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return list.retainAll(c);
	}

	@Override
	public List<T> reversed() {
		return list.reversed();
	}

	@Override
	public T set(int index, T element) {
		return list.set(index, element);
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public void sort(Comparator<? super T> c) {
		list.sort(c);
	}

	@Override
	public Spliterator<T> spliterator() {
		return list.spliterator();
	}

	@NonNull
	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}

	@Override
	public Object[] toArray() {
		return list.toArray();
	}

	@Override
	public <T1> T1[] toArray(T1[] a) {
		return list.toArray(a);
	}

	@Override
	public Stream<T> parallelStream() {
		return list.parallelStream();
	}

	@Override
	public boolean removeIf(Predicate<? super T> filter) {
		return list.removeIf(filter);
	}

	@Override
	public Stream<T> stream() {
		return list.stream();
	}

	@Override
	public <T1> T1[] toArray(IntFunction<T1[]> generator) {
		return list.toArray(generator);
	}

	@Override
	public void forEach(Consumer<? super T> action) {
		list.forEach(action);
	}
}
