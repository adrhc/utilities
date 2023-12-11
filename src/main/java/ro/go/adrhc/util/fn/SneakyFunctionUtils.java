package ro.go.adrhc.util.fn;

import com.rainerhahnekamp.sneakythrow.functional.SneakyFunction;
import lombok.experimental.UtilityClass;

import java.util.Optional;
import java.util.function.Function;

@UtilityClass
public class SneakyFunctionUtils {
    public static <T, R, E extends Exception>
    SneakyFunction<T, Optional<R>, E> toOptionalResult(Function<T, R> fn) {
        return t -> Optional.ofNullable(fn.apply(t));
    }
}
