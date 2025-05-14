package ro.go.adrhc.util.collection;

import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

public class ListUtils {
	public static <T> List<T> limit(int maxSize, Collection<T> collection) {
		return collection.stream().limit(maxSize).toList();
	}

	public static List<Pattern> emptyIfNull(List<Pattern> list) {
		return list == null ? List.of() : list;
	}
}
