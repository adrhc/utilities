package ro.go.adrhc.util;

import java.util.HashSet;
import java.util.List;

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

	public static <T extends Comparable<T>> int compareLists(List<T> list, List<T> other) {
		return compareLists(list, other, true);
	}

	public static <T extends Comparable<T>> int compareLists(List<T> list,
		List<T> other, boolean equalContentMeansEqualListsNoMatterTheOrder) {
		if (list.size() > other.size()) {
			return 1;
		} else if (list.size() < other.size()) {
			return -1;
		} else if (equalContentMeansEqualListsNoMatterTheOrder
		           && new HashSet<>(list).containsAll(other)) {
			return 0;
		}
		for (int i = 0; i < list.size(); i++) {
			int comparison = compareComparable(list.get(i), other.get(i));
			if (comparison != 0) {
				return comparison;
			}
		}
		return 0;
	}
}
