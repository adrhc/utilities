package ro.go.adrhc.util.fn;

import com.rainerhahnekamp.sneakythrow.functional.SneakyFunction;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@UtilityClass
@Slf4j
public class FunctionUtils {
	public static <R, E extends Exception>
	Optional<R> failToEmpty(SneakyIntFunction<R, E> sneakyFn, Integer fnParam) {
		try {
			return Optional.ofNullable(sneakyFn.apply(fnParam));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return Optional.empty();
	}

	public static <T, R, E extends Exception> R failToNull(SneakyFunction<T, R, E> sneakyFn, T t) {
		try {
			return sneakyFn.apply(t);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
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
}
