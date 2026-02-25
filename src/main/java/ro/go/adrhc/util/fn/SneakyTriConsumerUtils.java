package ro.go.adrhc.util.fn;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class SneakyTriConsumerUtils {
	public static <T, U, V, E extends Exception> void
	runSilently(SneakyTriConsumer<T, U, V, E> sneakyConsumer, T t, U u, V v) {
		try {
			sneakyConsumer.accept(t, u, v);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
