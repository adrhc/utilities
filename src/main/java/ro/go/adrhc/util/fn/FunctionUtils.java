package ro.go.adrhc.util.fn;

import java.util.Optional;
import java.util.function.Function;

public class FunctionUtils {
	public static <T, R>
	Function<T, Optional<R>> toOptionalResult(Function<T, R> fn) {
		return t -> Optional.of(fn.apply(t));
	}
}
