package ro.go.adrhc.util.fn;

import lombok.experimental.UtilityClass;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

@UtilityClass
public class UnaryOperatorUtils {
	public static <T> UnaryOperator<T> toUnaryOperator(Consumer<? super T> consumer) {
		return t -> {
			consumer.accept(t);
			return t;
		};
	}
}
