package ro.go.adrhc.util.fn;

import lombok.experimental.UtilityClass;

import java.util.function.Consumer;

@UtilityClass
public class ConsumerUtils {
	public static <T, R> R useAndReturnNull(Consumer<T> consumer, T t) {
		consumer.accept(t);
		return null;
	}
}
