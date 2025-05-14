package ro.go.adrhc.util.fn;

import com.rainerhahnekamp.sneakythrow.functional.SneakyFunction;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@UtilityClass
@Slf4j
public class OptionalOutFnUtils {
	public static <T, R, E extends Exception>
	Optional<R> failToEmpty(SneakyFunction<T, Optional<R>, E> sneakyFn, T t) {
		try {
			return sneakyFn.apply(t);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return Optional.empty();
	}
}
