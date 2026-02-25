package ro.go.adrhc.util.fn;

import com.rainerhahnekamp.sneakythrow.functional.SneakyBiConsumer;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@UtilityClass
@Slf4j
public class SneakyBiConsumerUtils {
	public static <T, U, E extends Exception> boolean
	failToFalse(SneakyBiConsumer<T, U, E> sneakyBiConsumer, T t, U u) {
		try {
			sneakyBiConsumer.accept(t, u);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return false;
	}

	public static <T, U, E extends Exception> void
	runSilently(SneakyBiConsumer<T, U, E> sneakyBiConsumer, T t, U u) {
		try {
			sneakyBiConsumer.accept(t, u);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
