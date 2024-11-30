package ro.go.adrhc.util.fn;

import com.rainerhahnekamp.sneakythrow.functional.SneakyFunction;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.function.Function;

@UtilityClass
@Slf4j
public class FunctionFactory {
	public static <T, R, E extends Exception> Function<T, R>
	of(SneakyFunction<T, R, E> sneakyFn) {
		return t -> {
			try {
				return sneakyFn.apply(t);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return null;
			}
		};
	}

	public static <R, E extends Exception>
	Optional<R> failToEmpty(SneakyIntFunction<R, E> sneakyFn, Integer fnParam) {
		try {
			return Optional.ofNullable(sneakyFn.apply(fnParam));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return Optional.empty();
	}

	public static <T, R, E extends Exception>
	Optional<R> failToEmpty(SneakyFunction<T, R, E> sneakyFn, T t) {
		try {
			return Optional.ofNullable(sneakyFn.apply(t));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return Optional.empty();
	}

	public static <T, R, E extends Exception>
	Function<T, Optional<R>> emptyFailResultFn(SneakyFunction<T, R, E> sneakyFn) {
		return t -> failToEmpty(sneakyFn, t);
	}

	public static <T, R, E extends Exception> Function<T, R>
	nullFailResultFn(SneakyFunction<T, R, E> sneakyFn) {
		return t -> {
			try {
				return sneakyFn.apply(t);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			return null;
		};
	}

	public static <T, R>
	Function<T, Optional<R>> optionalResultFn(Function<T, R> fn) {
		return t -> Optional.of(fn.apply(t));
	}

	public static <T> Function<T, String> stringResultFn(Function<T, ?> function) {
		return t -> {
			Object value = function.apply(t);
			return value == null ? null : value.toString();
		};
	}
}
