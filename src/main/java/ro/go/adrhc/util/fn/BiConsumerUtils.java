package ro.go.adrhc.util.fn;

import lombok.experimental.UtilityClass;

import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;

@UtilityClass
public class BiConsumerUtils {
	/**
	 * Intention: transform a void operation into a Function returning "this".
	 * e.g. someObject.append(elem):
	 * biOp(SomeObjectClass::append) creates the function:
	 * (thiz, elem) { thiz.append(elem); return thiz; }
	 */
	public static <T, U> BinaryOperator<T> toBiOper(BiConsumer<T, U> biConsumer) {
		return (thiz, u) -> {
			biConsumer.accept(thiz, (U) u);
			return thiz;
		};
	}

	public static <T> BinaryOperator<T> unaryToBiOper(BiConsumer<T, T> biConsumer) {
		return (thiz, t) -> {
			biConsumer.accept(thiz, t);
			return thiz;
		};
	}
}
