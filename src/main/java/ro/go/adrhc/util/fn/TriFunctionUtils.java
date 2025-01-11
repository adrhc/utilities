package ro.go.adrhc.util.fn;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@UtilityClass
@Slf4j
public class TriFunctionUtils {
	public static <T, U, V, R, E extends Exception>
	Optional<R> failToEmpty(SneakyTriFunction<T, U, V, R, E> sneakyFn, T t, U u, V v) {
		try {
			return Optional.ofNullable(sneakyFn.apply(t, u, v));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return Optional.empty();
	}
}
