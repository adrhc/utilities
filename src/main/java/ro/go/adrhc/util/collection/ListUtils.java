package ro.go.adrhc.util.collection;

import java.util.Collection;
import java.util.List;

public class ListUtils {
	public static <T> List<T> limit(int maxSize, Collection<T> collection) {
		return collection.stream().limit(maxSize).toList();
	}
}
