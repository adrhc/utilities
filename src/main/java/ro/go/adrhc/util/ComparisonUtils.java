package ro.go.adrhc.util;

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
}
