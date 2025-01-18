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
	public static <P1, P2, R> Function<P2, R> toP2Fn(BiFunction<P1, P2, R> biFunction, P1 p1) {
		return p2 -> biFunction.apply(p1, p2);
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
