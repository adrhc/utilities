package ro.go.adrhc.util.fn;

import com.rainerhahnekamp.sneakythrow.functional.SneakyFunction;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.function.Function;

@UtilityClass
@Slf4j
public class SneakyFunctionUtils {
	public static <T, R, E extends Exception>
	Function<T, Optional<R>> toOptionalResultFn(SneakyFunction<T, R, E> fn) {
		return t -> {
			try {
				return Optional.ofNullable(fn.apply(t));
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			return Optional.empty();
		};
	}

	public static <T, R, E extends Exception> Function<T, R>
	toNullResultFn(SneakyFunction<T, R, E> sneakyfn) {
		return t -> {
			try {
				return sneakyfn.apply(t);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			return null;
		};
	}
}
