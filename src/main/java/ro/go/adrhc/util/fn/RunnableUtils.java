package ro.go.adrhc.util.fn;

import com.rainerhahnekamp.sneakythrow.functional.SneakyBiConsumer;
import com.rainerhahnekamp.sneakythrow.functional.SneakyConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class RunnableUtils {
	public static <T, E extends Exception> void runSilently(SneakyConsumer<T, E> sneakyConsumer, T t) {
		try {
			sneakyConsumer.accept(t);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public static <T, U, E extends Exception> void runSilently(
		SneakyBiConsumer<T, U, E> sneakyConsumer, T t, U u) {
		try {
			sneakyConsumer.accept(t, u);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public static <T, U, V, E extends Exception> void runSilently(
			SneakyTriConsumer<T, U, V, E> sneakyConsumer, T t, U u, V v) {
		try {
			sneakyConsumer.accept(t, u, v);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public static <T, U, V, W, E extends Exception> void runSilently(
		SneakyTetraConsumer<T, U, V, W, E> sneakyConsumer, T t, U u, V v, W w) {
		try {
			sneakyConsumer.accept(t, u, v, w);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
