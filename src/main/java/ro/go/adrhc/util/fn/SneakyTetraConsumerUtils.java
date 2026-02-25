package ro.go.adrhc.util.fn;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class SneakyTetraConsumerUtils {
	public static <T, U, V, W, E extends Exception> void runSilently(
		SneakyTetraConsumer<T, U, V, W, E> sneakyConsumer, T t, U u, V v, W w) {
		try {
			sneakyConsumer.accept(t, u, v, w);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
