package ro.go.adrhc.util.fn;

import com.rainerhahnekamp.sneakythrow.functional.SneakyFunction;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

@UtilityClass
@Slf4j
public class SneakyFunctionUtils {
	public static <T, R, E extends Exception>
	SneakyFunction<T, Optional<R>, E> toOptionalResult(Function<T, R> fn) {
		return t -> Optional.ofNullable(fn.apply(t));
	}

	public static <T, R, E extends Exception> Function<T, R>
	toFunction(R fallBackValue, SneakyFunction<T, R, E> sneakyfn) {
		return t -> {
			try {
				return sneakyfn.apply(t);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			return fallBackValue;
		};
	}

	public static <T, R, E extends Exception> Function<T, R>
	toFunction(Supplier<R> fallBackSupplier, SneakyFunction<T, R, E> sneakyfn) {
		return t -> {
			try {
				return sneakyfn.apply(t);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			return fallBackSupplier.get();
		};
	}
}
