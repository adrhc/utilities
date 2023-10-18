package ro.go.adrhc.util;

import com.rainerhahnekamp.sneakythrow.functional.SneakyFunction;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@UtilityClass
public class ConversionUtils {
	public static <T, R, E extends Exception> List<R> convertAll(
			SneakyFunction<T, Optional<R>, E> converter, Collection<T> items) throws E {
		List<R> result = new ArrayList<>();
		for (T t : items) {
			converter.apply(t).ifPresent(result::add);
		}
		return result;
	}

	public static <T, R> List<R> convertAll(Function<T, Optional<R>> converter, Collection<T> items) {
		return items.stream()
				.map(converter)
				.flatMap(Optional::stream)
				.toList();
	}
}
