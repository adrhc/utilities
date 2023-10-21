package ro.go.adrhc.util.conversion;

import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@UtilityClass
public class ConversionUtils {
	public static <T, R> List<R> convertAll(Function<T, R> converter, Collection<T> items) {
		return items.stream()
				.map(converter)
				.toList();
	}
}
