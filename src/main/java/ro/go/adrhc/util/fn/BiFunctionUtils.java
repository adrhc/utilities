package ro.go.adrhc.util.fn;

import lombok.experimental.UtilityClass;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

@UtilityClass
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
}
