package ro.go.adrhc.util.conversion;

import com.rainerhahnekamp.sneakythrow.functional.SneakyFunction;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

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

	public static <T, R> List<R> convertAll(Function<T, R> converter, Collection<T> items) {
		return items.stream()
				.map(converter)
				.toList();
	}
}
