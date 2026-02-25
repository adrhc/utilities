package ro.go.adrhc.util.fn;

import com.rainerhahnekamp.sneakythrow.functional.SneakyFunction;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.function.Function;

import static ro.go.adrhc.util.fn.OptionalOutFnUtils.failToEmpty;

@UtilityClass
@Slf4j
public class OptionalOutFnFactory {
	public static <T, R>
	Function<T, Optional<R>> toSilentFn(SneakyFunction<T, Optional<R>, ?> sneakyFn) {
		return t -> failToEmpty(sneakyFn, t);
	}
}
