package ro.go.adrhc.util.conversion;

import com.rainerhahnekamp.sneakythrow.functional.SneakyFunction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class OptionalResultConversionUtils {
	public static <T, R, E extends Exception> List<R> convertAllSneaky(
			SneakyFunction<T, Optional<R>, E> converter, Collection<T> items) throws E {
		List<R> result = new ArrayList<>();
		for (T t : items) {
			converter.apply(t).ifPresent(result::add);
		}
		return result;
	}

	public static <T, R> List<R> convertCollection(Function<T, Optional<R>> converter, Collection<T> items) {
		return items.stream().map(converter).flatMap(Optional::stream).toList();
	}

	public static <T, R> Stream<R> convertStream(Function<T, Optional<R>> converter, Stream<T> items) {
		return items.map(converter).flatMap(Optional::stream);
	}
}
