package ro.go.adrhc.util;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class ComparisonUtils {
	public static <T> int compareComparable(Comparable<T> comparable, T other) {
		if (comparable == null && other == null) {
			return 0;
		} else if (comparable == null) {
			return -1;
		} else if (other == null) {
			return 1;
		} else {
			return comparable.compareTo(other);
		}
	}

	public static <T extends Comparable<T>> int compareComparable(T comparable, T other) {
		if (comparable == null && other == null) {
			return 0;
		} else if (comparable == null) {
			return -1;
		} else if (other == null) {
			return 1;
		} else {
			return comparable.compareTo(other);
		}
	}

	/**
	 * Compares two collections by their size and, if they are
	 * of the same size, by their elements, ignoring their order.
	 */
	public static <T extends Comparable<T>> int
	compareCollections(Collection<T> set1, Collection<T> set2) {
		return compareCollections(set1, set2, true);
	}

	/**
	 * Compares two collections by their size and, if they are of the
	 * same size, by their elements, optionally ignoring their order.
	 */
	public static <T extends Comparable<T>> int compareCollections(
		Collection<T> set1, Collection<T> set2, boolean ignoreTheOrder) {
		List<T> list1 = List.copyOf(set1);
		List<T> list2 = List.copyOf(set2);
		return compareLists(list1, list2, ignoreTheOrder);
	}

	public static <T extends Comparable<T>> int
	compareLists(List<T> list, List<T> other, boolean ignoreTheOrder) {
		if (list.size() > other.size()) {
			return 1;
		} else if (list.size() < other.size()) {
			return -1;
		} else if (ignoreTheOrder && Set.copyOf(list).containsAll(other)) {
			return 0;
		}
		return compareElemsOnEqualPositions(list, other);
	}

	public static <T extends Comparable<T>> int
	compareElemsOnEqualPositions(List<T> list, List<T> other) {
		for (int i = 0; i < list.size(); i++) {
			int comparison = compareComparable(list.get(i), other.get(i));
			if (comparison != 0) {
				return comparison;
			}
		}
		return 0;
	}
}
