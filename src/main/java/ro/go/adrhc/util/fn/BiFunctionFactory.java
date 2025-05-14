package ro.go.adrhc.util.fn;

import lombok.experimental.UtilityClass;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

@UtilityClass
public class BiFunctionFactory {
	/**
	 * The first parameter (i.e. tFnOutcome) will be the BiFunction's result.
	 */
	public static <P1, P2> BiFunction<P1, P2, P1> toP1OutBiFn(BiConsumer<P1, P2> biConsumer) {
		return (p1, P2) -> {
			biConsumer.accept(p1, P2);
			return p1;
		};
	}
}
