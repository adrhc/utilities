package ro.go.adrhc.util.fn;

import com.rainerhahnekamp.sneakythrow.functional.SneakyBiConsumer;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;

@UtilityClass
@Slf4j
public class BiConsumerUtils {
	public static <T, U, E extends Exception> boolean
	failToFalse(SneakyBiConsumer<T, U, E> sneakyFn, T t, U u) {
		try {
			sneakyFn.accept(t, u);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return false;
	}

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
