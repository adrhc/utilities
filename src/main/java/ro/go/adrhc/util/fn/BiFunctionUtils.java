package ro.go.adrhc.util.fn;

import com.rainerhahnekamp.sneakythrow.functional.SneakyBiFunction;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

@UtilityClass
@Slf4j
public class BiFunctionUtils {
	public static <T1, T2, R> Function<T2, R> curry(
			BiFunction<T1, T2, R> biFunction, T1 t1) {
		return t2 -> biFunction.apply(t1, t2);
	}

	public static <T, U> BiFunction<T, U, T> toBiFn(BiConsumer<T, U> biConsumer) {
		return (thiz, u) -> {
			biConsumer.accept(thiz, u);
			return thiz;
		};
	}

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
