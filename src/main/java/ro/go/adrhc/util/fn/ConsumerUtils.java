package ro.go.adrhc.util.fn;

import com.rainerhahnekamp.sneakythrow.functional.SneakyConsumer;
import com.rainerhahnekamp.sneakythrow.functional.SneakyFunction;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@UtilityClass
@Slf4j
public class ConsumerUtils {
	public static <T, E extends Exception> Consumer<T>
	toSafeConsumer(SneakyFunction<T, ?, E> sneakyFn) {
		return t -> {
			try {
				sneakyFn.apply(t);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		};
	}

	public static <T, E extends Exception> Consumer<T>
	toSafeConsumer(SneakyConsumer<T, E> sneakyConsumer) {
		return t -> {
			try {
				sneakyConsumer.accept(t);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		};
	}

	public static <T> void ignore(T t) {
		// do nothing
	}
}
