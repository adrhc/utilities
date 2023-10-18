package ro.go.adrhc.util;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ConversionUtils {
	public static <T, R> List<R> convertAll(Function<T, Optional<R>> converter, Collection<T> items) {
		return items.stream()
				.map(converter)
				.flatMap(Optional::stream)
				.toList();
	}
}
