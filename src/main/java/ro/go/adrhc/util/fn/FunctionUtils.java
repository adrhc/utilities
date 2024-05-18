package ro.go.adrhc.util.fn;

import com.rainerhahnekamp.sneakythrow.functional.SneakyFunction;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.function.Function;

@Slf4j
public class FunctionUtils {
	public static <T, R>
	Function<T, Optional<R>> toOptionalResult(Function<T, R> fn) {
		return t -> Optional.of(fn.apply(t));
	}

	public static <T, R, E extends Exception>
	Function<T, Optional<R>> sneakyToOptionalResult(SneakyFunction<T, R, E> fn) {
		return t -> {
			try {
				return Optional.ofNullable(fn.apply(t));
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return Optional.empty();
			}
		};
	}

	public static <T> Function<T, String> string(Function<T, ?> function) {
		return t -> {
			Object value = function.apply(t);
			return value == null ? null : value.toString();
		};
	}
}
