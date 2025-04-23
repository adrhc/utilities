package ro.go.adrhc.util.fn;

import com.rainerhahnekamp.sneakythrow.functional.SneakyBiFunction;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@UtilityClass
@Slf4j
public class BiFunctionUtils {
	public static <T, U, R, E extends Exception>
	Optional<R> failToEmpty(SneakyBiFunction<T, U, R, E> sneakyFn, T t, U u) {
		try {
			return Optional.ofNullable(sneakyFn.apply(t, u));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return Optional.empty();
	}
}
