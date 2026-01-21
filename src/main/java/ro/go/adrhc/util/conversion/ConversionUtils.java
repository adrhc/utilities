package ro.go.adrhc.util.conversion;

import com.rainerhahnekamp.sneakythrow.functional.SneakyFunction;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static ro.go.adrhc.util.stream.StreamUtils.stream;

@UtilityClass
public class ConversionUtils {
	public static <T, R, E extends Exception> List<R>
	convertAllSneaky(SneakyFunction<T, R, E> converter, Collection<T> items) throws E {
		List<R> result = new ArrayList<>();
		for (T t : items) {
			result.add(converter.apply(t));
		}
		return result;
	}

	public static <T, R> Stream<R> convertStream(Function<T, R> converter, Stream<T> tStream) {
		return tStream.map(converter);
	}

	public static <T, R> List<R> convertCollection(Function<T, R> converter,
		Collection<T> tCollection) {
		return tCollection.stream().map(converter).toList();
	}

	public static <T, R> List<R> convertCollection(Function<T, R> converter,
		Iterable<T> tIterable) {
		return stream(tIterable).map(converter).toList();
	}
}
