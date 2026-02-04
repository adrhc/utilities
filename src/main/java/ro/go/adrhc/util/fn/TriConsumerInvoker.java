package ro.go.adrhc.util.fn;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TriConsumerInvoker {
	public static <T, U, V, E extends Exception> boolean
	failToFalse(SneakyTriConsumer<T, U, V, E> sneakyFn, T t, U u, V v) {
		try {
			sneakyFn.accept(t, u, v);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return false;
	}
}
