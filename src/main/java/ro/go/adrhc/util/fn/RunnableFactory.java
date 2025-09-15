package ro.go.adrhc.util.fn;

import com.rainerhahnekamp.sneakythrow.functional.SneakyConsumer;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class RunnableFactory {
	public static Runnable doNothing() {
		return () -> {
		};
	}

	public static <T, E extends Exception> Runnable
	toRunnable(SneakyConsumer<T, E> sneakyConsumer, T t) {
		return () -> {
			try {
				sneakyConsumer.accept(t);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		};
	}
}
