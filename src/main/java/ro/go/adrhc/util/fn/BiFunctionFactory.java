package ro.go.adrhc.util.fn;

import com.rainerhahnekamp.sneakythrow.functional.SneakyBiFunction;
import lombok.experimental.UtilityClass;

import java.util.Optional;
import java.util.function.Function;

import static ro.go.adrhc.util.fn.BiFunctionUtils.failToEmpty;

@UtilityClass
public class BiFunctionFactory {
	public static <T, U, R, E extends Exception>
	Function<U, Optional<R>> emptyFailResultFn(SneakyBiFunction<T, U, R, E> sneakyBiFn, T t) {
		return u -> failToEmpty(sneakyBiFn, t, u);
	}
}
