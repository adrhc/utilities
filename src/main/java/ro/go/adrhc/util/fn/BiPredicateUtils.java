package ro.go.adrhc.util.fn;

import lombok.experimental.UtilityClass;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;

@UtilityClass
public class BiPredicateUtils {
	public static <T> BinaryOperator<T> toBiOper(BiPredicate<T, T> biConsumer) {
		return (thiz, t) -> {
			biConsumer.test(thiz, t);
			return thiz;
		};
	}

	public static <T, C> BiFunction<T, C, T> toBiFn(BiPredicate<T, C> biPredicate) {
		return (thiz, c) -> {
			biPredicate.test(thiz, c);
			return thiz;
		};
	}

}
