package ro.go.adrhc.util.fn;

import lombok.experimental.UtilityClass;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

@UtilityClass
public class BiFunctionFactory {
	/**
	 * The first parameter (i.e. tFnOutcome) will be the BiFunction's result.
	 */
	public static <T, U> BiFunction<T, U, T> toP1OutBiFn(BiConsumer<T, U> biConsumer) {
		return (tFnOutcome, u) -> {
			biConsumer.accept(tFnOutcome, u);
			return tFnOutcome;
		};
	}
}
