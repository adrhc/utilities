package ro.go.adrhc.util;

import com.rainerhahnekamp.sneakythrow.functional.SneakyBiFunction;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class SneakUtils {
	public static <T, U, R, E extends Exception> Optional<R>
	toOptional(SneakyBiFunction<T, U, R, E> fn, T t, U u) {
		try {
			return Optional.ofNullable(fn.apply(t, u));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return Optional.empty();
	}
}
