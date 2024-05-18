package ro.go.adrhc.util.fn;

import com.rainerhahnekamp.sneakythrow.functional.SneakyBiFunction;
import com.rainerhahnekamp.sneakythrow.functional.SneakyFunction;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@UtilityClass
@Slf4j
public class SneakyBiFunctionUtils {
	public static <T1, T2, R, E extends Exception> SneakyFunction<T2, R, E> curry(
			SneakyBiFunction<T1, T2, R, E> biFunction, T1 t1) {
		return t2 -> biFunction.apply(t1, t2);
	}

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
